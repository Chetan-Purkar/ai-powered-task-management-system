package com.aitask.authservice.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aitask.authservice.model.UserAuth;
import com.aitask.authservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ---------- REGISTER ----------
    public UserAuth register(String username, String rawPassword, String email) {

        if (userRepository.existsByUsername(username)) {
            throw new IllegalStateException("Username already exists");
        }

        if (userRepository.findByEmail(email) != null) {
            throw new IllegalStateException("Email already registered");
        }

        UserAuth user = UserAuth.builder()
                .username(username)
                .password(passwordEncoder.encode(rawPassword))
                .email(email)
                .build();

        return userRepository.save(user);
    }

    // ---------- GET BY ID ----------
    public UserAuth getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new jakarta.persistence.EntityNotFoundException("User not found with id = " + id)
                );
    }
}
