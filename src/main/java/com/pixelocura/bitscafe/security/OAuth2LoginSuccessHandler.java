package com.pixelocura.bitscafe.security;

import com.pixelocura.bitscafe.model.entity.User;
import com.pixelocura.bitscafe.repository.RoleRepository;
import com.pixelocura.bitscafe.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OidcUser oidcUser = (OidcUser) oauthToken.getPrincipal();
        // Print all OIDC attributes in pretty JSON for debug/analysis
        try {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
            String prettyJson = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(oidcUser.getAttributes());
            System.out.println("[OAuth2LoginSuccessHandler] OIDC attributes (pretty JSON):\n" + prettyJson);
        } catch (Exception e) {
            System.out.println("[OAuth2LoginSuccessHandler] Failed to pretty-print OIDC attributes: " + e);
        }
        String email = oidcUser.getEmail();
        String name = oidcUser.getFullName();

        System.out.println("[OAuth2LoginSuccessHandler] OAuth2 login attempt for email: " + email);
        System.out.println("[OAuth2LoginSuccessHandler] OIDC name: " + name);
        // Find or create user
        Optional<User> userOpt = userRepository.findByEmail(email);
        System.out.println("[OAuth2LoginSuccessHandler] User found in DB: " + userOpt.isPresent());
        User user = userOpt.orElseGet(() -> {
            System.out.println("[OAuth2LoginSuccessHandler] Creating new user for email: " + email);
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(name);
            // Username: use part before @ and remove invalid chars
            String usernameCandidate = email != null ? email.split("@")[0].replaceAll("[^a-zA-Z0-9_-]", "") : "user";
            System.out
                    .println("[OAuth2LoginSuccessHandler] Username candidate before validation: " + usernameCandidate);
            newUser.setUsername(usernameCandidate);
            // Lastname: use parsed or default to 'Google' if too short
            String[] nameParts = name != null ? name.trim().split(" ") : new String[] { "" };
            System.out.println("[OAuth2LoginSuccessHandler] Name parts: " + java.util.Arrays.toString(nameParts));
            String lastname = (nameParts.length > 1) ? nameParts[nameParts.length - 1] : "Google";
            if (lastname.length() < 2)
                lastname = "Google";
            newUser.setLastname(lastname);
            System.out.println("[OAuth2LoginSuccessHandler] Final username: " + usernameCandidate);
            System.out.println("[OAuth2LoginSuccessHandler] Final lastname: " + lastname);
            newUser.setPasswordHash("oauth2-google");
            newUser.setCountry(com.pixelocura.bitscafe.model.enums.Country.PE);
            System.out.println(
                    "[OAuth2LoginSuccessHandler] Set country to: " + com.pixelocura.bitscafe.model.enums.Country.PE);
            // Fetch DEVELOPER role from DB
            System.out.println("[OAuth2LoginSuccessHandler] Fetching DEVELOPER role from DB");
            com.pixelocura.bitscafe.model.entity.Role developerRole = roleRepository
                    .findByName(com.pixelocura.bitscafe.model.enums.ERole.DEVELOPER)
                    .orElseThrow(() -> new RuntimeException("DEVELOPER role not found in DB"));
            System.out.println("[OAuth2LoginSuccessHandler] DEVELOPER role entity: " + developerRole);
            newUser.setRole(developerRole);
            System.out.println("[OAuth2LoginSuccessHandler] Assigned DEVELOPER role from DB");
            User saved = userRepository.save(newUser);
            System.out.println("[OAuth2LoginSuccessHandler] New user saved with ID: " + saved.getId());
            return saved;
        });
        System.out.println("[OAuth2LoginSuccessHandler] Using user ID: " + user.getId());
        System.out.println("[OAuth2LoginSuccessHandler] User entity: " + user);
        // Create a UserPrincipal for JWT
        UserPrincipal principal = new UserPrincipal();
        principal.setId(user.getId());
        principal.setEmail(user.getEmail());
        principal.setUser(user);
        principal.setAuthorities(getAuthoritiesForUser(user));
        System.out.println("[OAuth2LoginSuccessHandler] UserPrincipal created for JWT: " + principal);
        // Generate JWT
        String jwt = tokenProvider.createAccessToken(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(principal, null,
                        principal.getAuthorities()));
        System.out.println("[OAuth2LoginSuccessHandler] JWT generated: " + jwt);
        // Redirect to frontend with JWT
        String redirectUrl = "http://localhost:4200/login-success?token=" + jwt;
        System.out.println("[OAuth2LoginSuccessHandler] Redirecting to: " + redirectUrl);
        response.sendRedirect(redirectUrl);
    }

    private Collection<? extends GrantedAuthority> getAuthoritiesForUser(User user) {
        if (user.getRoleName() != null) {
            return Collections.singletonList(new SimpleGrantedAuthority(user.getRoleName().name()));
        }
        return Collections.emptyList();
    }
}
