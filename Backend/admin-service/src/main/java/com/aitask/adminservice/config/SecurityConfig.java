package com.aitask.adminservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.aitask.adminservice.security.GatewayHeaderAuthFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final GatewayHeaderAuthFilter gatewayHeaderAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            // ---------------- ROLE-BASED AUTHORIZATION ----------------
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/actuator/**", "/admin/test").permitAll()
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
            )
            // ---------------- EXCEPTION HANDLING FOR 401/403 ----------------
            .exceptionHandling(ex -> ex
                    .authenticationEntryPoint((req, res, e) -> {
                        res.setStatus(HttpStatus.UNAUTHORIZED.value());
                        res.setContentType("application/json");
                        res.getWriter().write("""
                            {
                              "status": 401,
                              "error": "UNAUTHORIZED",
                              "message": "Authentication required",
                              "path": "%s",
                              "timestamp": "%s"
                            }
                        """.formatted(req.getRequestURI(), java.time.LocalDateTime.now()));
                    })
                    .accessDeniedHandler((req, res, e) -> {
                        res.setStatus(HttpStatus.FORBIDDEN.value());
                        res.setContentType("application/json");
                        res.getWriter().write("""
                            {
                              "status": 403,
                              "error": "FORBIDDEN",
                              "message": "Access denied",
                              "path": "%s",
                              "timestamp": "%s"
                            }
                        """.formatted(req.getRequestURI(), java.time.LocalDateTime.now()));
                    })
            )
            // ---------------- CUSTOM HEADER FILTER ----------------
            .addFilterBefore(
                gatewayHeaderAuthFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}
