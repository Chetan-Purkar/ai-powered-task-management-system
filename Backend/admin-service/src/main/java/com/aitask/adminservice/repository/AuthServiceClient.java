package com.aitask.adminservice.repository;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aitask.adminservice.config.FeignConfig;
import com.aitask.adminservice.dto.CreateAdminProfileDTO;
import com.aitask.common.enums.Role;
import com.aitask.common.enums.Status;

@FeignClient(name = "auth-service", configuration = FeignConfig.class)
public interface AuthServiceClient {

    @GetMapping("/internal/auth/admin/{id}")
    CreateAdminProfileDTO getUserById(@PathVariable("id") Long id);
    
    @PutMapping("/internal/auth/users/{userId}/role")
    void updateUserRole(
        @PathVariable Long userId,
        @RequestParam Role role
    );

    @PutMapping("/internal/auth/users/{userId}/status")
	void updateUserStatus(
		@PathVariable Long userId, 
		@RequestParam Status status
	);

    
}
