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

    public UserAuth register(String username, String rawPassword, String email) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("User already exists");
        }
        UserAuth u = UserAuth.builder()
                .username(username)
                .password(passwordEncoder.encode(rawPassword))
                .email(email)
                .build();
        return userRepository.save(u);
    }

    public UserAuth getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found: " + id)
                );
    }
    
    
}
