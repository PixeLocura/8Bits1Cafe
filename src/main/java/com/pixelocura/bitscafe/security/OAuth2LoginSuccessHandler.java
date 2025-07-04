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
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
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
        Object principal = oauthToken.getPrincipal();
        final String email;
        final String name;
        final String usernameCandidate;
        final String lastnameValue;
        if (principal instanceof OidcUser oidcUser) {
            // Google OIDC
            try {
                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
                String prettyJson = mapper.writerWithDefaultPrettyPrinter()
                        .writeValueAsString(oidcUser.getAttributes());
                System.out.println("[OAuth2LoginSuccessHandler] OIDC attributes (pretty JSON):\n" + prettyJson);
            } catch (Exception e) {
                System.out.println("[OAuth2LoginSuccessHandler] Failed to pretty-print OIDC attributes: " + e);
            }
            email = oidcUser.getEmail();
            name = oidcUser.getFullName();
            usernameCandidate = email != null ? email.split("@")[0].replaceAll("[^a-zA-Z0-9_-]", "") : "user";
            lastnameValue = "";
        } else if (principal instanceof DefaultOAuth2User discordUser) {
            // Discord
            try {
                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                String prettyJson = mapper.writerWithDefaultPrettyPrinter()
                        .writeValueAsString(discordUser.getAttributes());
                System.out.println("[OAuth2LoginSuccessHandler] Discord attributes (pretty JSON):\n" + prettyJson);
            } catch (Exception e) {
                System.out.println("[OAuth2LoginSuccessHandler] Failed to pretty-print Discord attributes: " + e);
            }
            email = (String) discordUser.getAttribute("email");
            String discordUsername = (String) discordUser.getAttribute("username");
            Object globalNameObj = discordUser.getAttribute("global_name");
            String globalName = globalNameObj != null ? globalNameObj.toString() : null;
            if (globalName != null && !globalName.isBlank()) {
                String[] parts = globalName.trim().split("\\s+");
                if (parts.length == 1) {
                    name = parts[0];
                    lastnameValue = "";
                } else {
                    name = parts[0];
                    lastnameValue = String.join(" ", java.util.Arrays.copyOfRange(parts, 1, parts.length));
                }
            } else {
                name = discordUsername;
                lastnameValue = "";
            }
            usernameCandidate = discordUsername;
        } else {
            throw new IllegalStateException("Unknown OAuth2 principal type: " + principal.getClass());
        }
        System.out.println("[OAuth2LoginSuccessHandler] OAuth2 login attempt for email: " + email);
        System.out.println("[OAuth2LoginSuccessHandler] Name: " + name);
        // Find or create user
        Optional<User> userOpt = userRepository.findByEmail(email);
        System.out.println("[OAuth2LoginSuccessHandler] User found in DB: " + userOpt.isPresent());
        User user = userOpt.orElseGet(() -> {
            System.out.println("[OAuth2LoginSuccessHandler] Creating new user for email: " + email);
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setUsername(usernameCandidate);
            newUser.setName(name);
            newUser.setLastname(lastnameValue);
            // Lastname: set to empty string if not provided (DB requires NOT NULL)
            newUser.setLastname("");
            System.out.println("[OAuth2LoginSuccessHandler] Final username: " + usernameCandidate);
            System.out.println("[OAuth2LoginSuccessHandler] Final lastname: '' (empty string)");
            // Password hash: set to empty string for OAuth2 users (DB requires NOT NULL)
            newUser.setPasswordHash("");
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
        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setId(user.getId());
        userPrincipal.setEmail(user.getEmail());
        userPrincipal.setUser(user);
        userPrincipal.setAuthorities(getAuthoritiesForUser(user));
        System.out.println("[OAuth2LoginSuccessHandler] UserPrincipal created for JWT: " + userPrincipal);
        // Generate JWT
        String jwt = tokenProvider.createAccessToken(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(userPrincipal, null,
                        userPrincipal.getAuthorities()));
        System.out.println("[OAuth2LoginSuccessHandler] JWT generated: " + jwt);
        // Redirect to frontend with JWT
        String frontendUrl = System.getenv("FRONTEND_URL");
        String redirectUrl;
        if (frontendUrl != null && !frontendUrl.isBlank()) {
            frontendUrl = frontendUrl.replaceAll("/+$", ""); // Remove trailing slash if present
            redirectUrl = frontendUrl + "/login-success?token=" + jwt;
        } else {
            redirectUrl = "http://localhost:4200/login-success?token=" + jwt; // fallback to localhost
        }
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
