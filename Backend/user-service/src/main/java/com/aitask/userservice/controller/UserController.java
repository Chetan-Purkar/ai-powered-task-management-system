package com.aitask.userservice.controller;

import com.aitask.userservice.dto.UserDTO;
import com.aitask.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // ===================== Get current user =====================
    @GetMapping("/current")
    public ResponseEntity<UserDTO> getCurrentUser(
            @RequestHeader(value = "X-USER-EMAIL", required = false) String email
    ) {
        if (email == null) {
            return ResponseEntity.status(401).build();
        }
        UserDTO user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    // ===================== Get all users (admin only) =====================
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(
            @RequestHeader(value = "X-USER-ROLE", required = false) String role
    ) {
        if (role == null || !role.equalsIgnoreCase("ADMIN")) {
            return ResponseEntity.status(403).build();
        }
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // ===================== Get user by email =====================
    @GetMapping("/by-email")
    public ResponseEntity<UserDTO> getUserByEmail(
            @RequestParam String email
    ) {
        UserDTO user = userService.getUserByEmail(email);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }
}
