package com.aitask.taskservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.aitask.taskservice.security.GatewayHeaderAuthFilter;

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

            .authorizeHttpRequests(auth -> auth

                // ---------- Public / Infra ----------
                .requestMatchers(
                    "/actuator/**",
                    "/tasks/test"
                ).permitAll()

                // ---------- Task APIs ----------
                .requestMatchers("/tasks/**")
                .hasAnyRole("USER", "ADMIN")

                // ---------- Admin-only (optional future) ----------
                .requestMatchers("/admin/tasks/**")
                .hasRole("ADMIN")

                // ---------- Everything else ----------
                .anyRequest().authenticated()
            )

            // ---------- Gateway Header Authentication ----------
            .addFilterBefore(
                gatewayHeaderAuthFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}
