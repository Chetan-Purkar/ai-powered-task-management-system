package com.aitask.authservice.service;

import org.springframework.stereotype.Service;

import com.aitask.authservice.model.UserAuth;
import com.aitask.authservice.repository.UserRepository;
import com.aitask.common.enums.Role;
import com.aitask.common.enums.Status;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAuthService {

    private final UserRepository userRepository;

    public void updateRole(Long userId, Role role) {
        UserAuth user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(role);
        userRepository.save(user);
    }
    
    public void updateStatus(Long userId, Status status) {
        UserAuth user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setStatus(status);
        userRepository.save(user);
    }
}
