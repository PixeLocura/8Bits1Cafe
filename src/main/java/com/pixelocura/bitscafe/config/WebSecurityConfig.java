package com.pixelocura.bitscafe.config;

import com.pixelocura.bitscafe.security.JWTConfigurer;
import com.pixelocura.bitscafe.security.JWTFilter;
import com.pixelocura.bitscafe.security.JwtAuthenticationEntryPoint;
import com.pixelocura.bitscafe.security.TokenProvider;
//import com.pixelocura.bitscafe.security.CustomAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private final TokenProvider tokenProvider;
    private final JWTFilter jwtRequestFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> {}) // usa config por defecto
            .exceptionHandling(e -> e.authenticationEntryPoint(jwtAuthenticationEntryPoint)  )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/login").permitAll()
                .requestMatchers("/api/v1/auth/register/**").permitAll()
                                   .requestMatchers("/auth/login").permitAll()
                .requestMatchers("/auth/register/developer").permitAll()
                .requestMatchers("/auth/register/admin").permitAll()
                                   .requestMatchers("/api/v1/auth/register/admin").permitAll()
                                   .requestMatchers("/api/v1/auth/register/developer").permitAll()
                .requestMatchers("/auth/register/**").permitAll()
                .requestMatchers("/api/v1/swagger-ui/**",
                                 "/v3/api-docs/**",
                                 "/swagger-ui.html",
                                 "/swagger-ui/**",
                                 "/webjars/**").permitAll()
                .requestMatchers("/users").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .apply(new JWTConfigurer(tokenProvider));

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200") // o el dominio de tu frontend desplegado
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
        }
    };
}

}
