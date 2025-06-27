package com.pixelocura.bitscafe.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JWTFilter extends GenericFilterBean {

    private final TokenProvider tokenProvider;

    private static final List<String> EXCLUDED_PATHS = List.of(
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/api/v1/auth/register/admin",
            "/api/v1/auth/register/developer",
            "/swagger-ui",
            "/v3/api-docs",
            "/swagger-ui.html"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

                HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String path = httpServletRequest.getRequestURI();
        
            if (isExcluded(path)) {
                chain.doFilter(request, response);
                return;
            }
        
            String bearerToken = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        
            System.out.println("=== JWTFilter DEBUG ===");
            System.out.println("Request path: " + path);
            System.out.println("Header Authorization: " + bearerToken);
        
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
                String token = bearerToken.substring(7);
                System.out.println("Resolved Token: " + token);
        
                if (tokenProvider.validateToken(token)) {
                    System.out.println("Token is valid");
                    Authentication authentication = tokenProvider.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    System.out.println("Token is INVALID");
                }
            } else {
                System.out.println("Bearer Token missing or malformed");
            }
        
            chain.doFilter(request, response);
    }

    private boolean isExcluded(String path) {
        return EXCLUDED_PATHS.stream().anyMatch(path::startsWith);
    }
}
