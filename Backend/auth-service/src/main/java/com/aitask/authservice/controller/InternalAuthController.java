package com.aitask.authservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aitask.authservice.dto.AuthUserDTO;
import com.aitask.authservice.model.UserAuth;
import com.aitask.authservice.service.UserAuthService;
import com.aitask.authservice.service.UserService;
import com.aitask.common.enums.Role;
import com.aitask.common.enums.Status;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/internal/auth")
@RequiredArgsConstructor
public class InternalAuthController {

    private final UserService service;
    private final UserAuthService userAuthService;

    @GetMapping("/users/{id}")
    public AuthUserDTO getUserById(@PathVariable Long id) {
        UserAuth user = service.getById(id);

        return new AuthUserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
    
    @GetMapping("/admin/{id}")
    public AuthUserDTO getAdminById(@PathVariable Long id) {
        UserAuth user = service.getById(id);

        return new AuthUserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
    
    @PutMapping("/users/{userId}/role")
    public ResponseEntity<Void> updateUserRole(
            @PathVariable Long userId,
            @RequestParam Role role
    ) {
        userAuthService.updateRole(userId, role);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/users/{userId}/status")
    public ResponseEntity<Void> updateUserStatus(
            @PathVariable Long userId,
            @RequestParam Status status
    ) {
        userAuthService.updateStatus(userId, status);
        return ResponseEntity.ok().build();
    }
}
