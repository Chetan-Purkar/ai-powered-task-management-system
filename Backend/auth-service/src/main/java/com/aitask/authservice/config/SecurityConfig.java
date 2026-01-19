package com.aitask.authservice.config;

import com.aitask.authservice.repository.UserRepository;
import com.aitask.common.enums.Role;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import java.util.List;
@Configuration
public class SecurityConfig {

    private final UserRepository userRepository;

    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {

        return identifier -> userRepository
                .findByUsernameOrEmail(identifier, identifier)   // 🔥 BEST PRACTICE
                .map(user -> {

                    Role role = user.getRole();

                    if (role == null) {
                        throw new UsernameNotFoundException("User role not assigned");
                    }

                    return new org.springframework.security.core.userdetails.User(
                            user.getUsername(),          // principal
                            user.getPassword(),          // encoded password
                            List.of(new SimpleGrantedAuthority("ROLE_" + role.name()))
                    );
                })
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found")
                );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity http,
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService) throws Exception {

        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                            "/auth/login",
                            "/auth/register",
                            "/auth/public-key",
                            "/actuator/**",
                            "/internal/**"
                    ).permitAll()
                    .requestMatchers("/internal/auth/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
