package com.aitask.adminservice.controller;

import com.aitask.adminservice.dto.AdminAuthDTO;
import com.aitask.adminservice.dto.CreateAdminProfileDTO;
import com.aitask.adminservice.model.AdminProfile;
import com.aitask.adminservice.repository.AuthServiceClient;
import com.aitask.adminservice.service.AdminService;
import com.aitask.common.enums.Role;
import com.aitask.common.enums.Status;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final AuthServiceClient authServiceClient;

    // ---------------- CREATE ADMIN PROFILE ----------------
    @PostMapping("/createprofile")
    public ResponseEntity<AdminProfile> createAdmin(
            @RequestBody CreateAdminProfileDTO profile,
            @RequestHeader("X-User-Id") Long adminId) {

        if (profile == null) {
            throw new IllegalArgumentException("Profile data is required");
        }

        System.out.println("Creating admin profile for adminId = " + adminId);
        profile.setAdminId(adminId);

        AdminProfile savedProfile = adminService.createAdminProfile(profile, adminId);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProfile);
    }   

    // ---------------- CURRENT ADMIN PROFILE ----------------
    @GetMapping("/getprofile")
    public ResponseEntity<AdminAuthDTO> getMyProfile(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-Username") String username,
            @RequestHeader("X-Roles") String role
    ) {

        AdminProfile profile = adminService.getAdminProfile(userId);

        return ResponseEntity.ok(
                AdminAuthDTO.builder()
                        .userId(userId)
                        .username(username)
                        .role(role)
                        .profile(profile)
                        .build()
        );
    }
    
    
    @PutMapping("/roles/users/{userId}")
    public ResponseEntity<String> updateUserRole(
            @PathVariable Long userId,
            @RequestParam Role role,
            Authentication authentication
    ) {
        Long adminId = (Long) authentication.getDetails();

        if (adminId.equals(userId)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Admin cannot change their own role");
        }

        authServiceClient.updateUserRole(userId, role);

        return ResponseEntity.ok("Role updated successfully");
    }

    
    @PutMapping("/status/users/{userId}")
    public ResponseEntity<String> updateUserStatus(
            @PathVariable Long userId,
            @RequestParam Status status,
            Authentication authentication
    ) {
        Long adminId = (Long) authentication.getDetails();
        
        if (adminId.equals(userId)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Admin cannot change their own status");
        }

        authServiceClient.updateUserStatus(userId, status);

        return ResponseEntity.ok("Status updated successfully");
    }

    
    
    

}
