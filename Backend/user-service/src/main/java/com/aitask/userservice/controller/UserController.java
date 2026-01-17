package com.aitask.userservice.controller;

import com.aitask.userservice.dto.UserMeDTO;
import com.aitask.userservice.entity.UserProfile;
import com.aitask.userservice.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // ---------------- CREATE PROFILE ----------------
    @PostMapping("/create")
    public ResponseEntity<UserProfile> createProfile(
            @RequestBody UserProfile profile,
            @RequestHeader("X-User-Id") Long userId
    ) {
        if (profile == null) {
            throw new IllegalArgumentException("Profile data is required");
        }

        profile.setUserId(userId);
        System.out.println("Create user profile for userId = " + userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createProfile(profile, userId));
    }

    // ---------------- CURRENT USER PROFILE ----------------
    @GetMapping("/me")
    public ResponseEntity<UserMeDTO> getMyProfile(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-Username") String username,
            @RequestHeader("X-Roles") String role
    ) {
        UserProfile profile = service.getProfileByUserId(userId);

        return ResponseEntity.ok(
                UserMeDTO.builder()
                        .userId(userId)
                        .username(username)
                        .role(role)
                        .profile(profile)
                        .build()
        );
    }

    // ---------------- ADMIN ONLY ----------------
    // ROLE CHECK IS HANDLED BY SECURITY CONFIG
    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getProfileByUserId(id));
    }

    // ---------------- TEST ENDPOINT ----------------
    @GetMapping("/testing")
    public String getStrings() {
        System.out.println("Testing Successful");
        return "This is user Controller";
    }
}
