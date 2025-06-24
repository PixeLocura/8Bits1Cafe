package com.pixelocura.bitscafe.security;

import com.pixelocura.bitscafe.exception.RoleNotFoundException;
import com.pixelocura.bitscafe.model.entity.User;
import com.pixelocura.bitscafe.model.entity.Developer;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenProvider {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.validity-in-seconds}")
    private long jwtValidityInSeconds;
    private Key key;
    private JwtParser jwtParser;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        jwtParser = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build();
    }

    public String createAccessToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.getUser();

        String role = authentication
                .getAuthorities()
                .stream()
                .findFirst()
                .orElseThrow(RoleNotFoundException::new)
                .getAuthority();

        JwtBuilder jwtBuilder = Jwts
                .builder()
                .setSubject(user.getEmail())
                .claim("role", role)
                .claim("userId", user.getId().toString())
                .claim("username", user.getUsername())
                .claim("email", user.getEmail())
                .claim("name", user.getName());

        // Add developer information if user is a developer
        if (user.getDeveloperProfile() != null) {
            Developer dev = user.getDeveloperProfile();
            jwtBuilder
                .claim("developerId", dev.getId().toString())
                .claim("developerName", dev.getName());
        }

        return jwtBuilder
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(new Date(System.currentTimeMillis() + jwtValidityInSeconds * 1000))
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();

        String role = claims.get("role").toString();
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));

        // Create UserPrincipal with minimal information from token
        UserPrincipal principal = new UserPrincipal();
        principal.setEmail(claims.getSubject());
        principal.setAuthorities(authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String token) {
        try {
            jwtParser.parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
