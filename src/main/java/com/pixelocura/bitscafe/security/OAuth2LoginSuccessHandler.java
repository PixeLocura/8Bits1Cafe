package com.pixelocura.bitscafe.security;

import com.pixelocura.bitscafe.model.entity.User;
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
    private final TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OidcUser oidcUser = (OidcUser) oauthToken.getPrincipal();
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
            // Try to parse lastname from name (assume last word is lastname)
            String[] nameParts = name != null ? name.trim().split(" ") : new String[] { "" };
            if (nameParts.length > 1) {
                newUser.setLastname(nameParts[nameParts.length - 1]);
                System.out.println("[OAuth2LoginSuccessHandler] Parsed lastname: " + nameParts[nameParts.length - 1]);
            } else {
                newUser.setLastname("");
                System.out.println("[OAuth2LoginSuccessHandler] No lastname found, set to empty string");
            }
            newUser.setUsername(email);
            newUser.setPasswordHash("oauth2-google");
            newUser.setCountry(com.pixelocura.bitscafe.model.enums.Country.PE);
            System.out.println(
                    "[OAuth2LoginSuccessHandler] Set country to: " + com.pixelocura.bitscafe.model.enums.Country.PE);
            com.pixelocura.bitscafe.model.entity.Role developerRole = new com.pixelocura.bitscafe.model.entity.Role();
            developerRole.setName(com.pixelocura.bitscafe.model.enums.ERole.DEVELOPER);
            newUser.setRole(developerRole);
            System.out.println("[OAuth2LoginSuccessHandler] Assigned DEVELOPER role");
            User saved = userRepository.save(newUser);
            System.out.println("[OAuth2LoginSuccessHandler] New user saved with ID: " + saved.getId());
            return saved;
        });
        System.out.println("[OAuth2LoginSuccessHandler] Using user ID: " + user.getId());
        // Create a UserPrincipal for JWT
        UserPrincipal principal = new UserPrincipal();
        principal.setId(user.getId());
        principal.setEmail(user.getEmail());
        principal.setUser(user);
        principal.setAuthorities(getAuthoritiesForUser(user));
        System.out.println("[OAuth2LoginSuccessHandler] UserPrincipal created for JWT");
        // Generate JWT
        String jwt = tokenProvider.createAccessToken(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(principal, null,
                        principal.getAuthorities()));
        System.out.println("[OAuth2LoginSuccessHandler] JWT generated: " + jwt);
        // Redirect to frontend with JWT
        String redirectUrl = "http://192.168.1.40:4200/login-success?token=" + jwt;
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
