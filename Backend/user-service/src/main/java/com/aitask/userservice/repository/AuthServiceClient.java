package com.aitask.userservice.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.aitask.userservice.config.FeignConfig;
import com.aitask.userservice.dto.CreateUserDTO;

@FeignClient(name = "auth-service", configuration = FeignConfig.class)
public interface AuthServiceClient {

    @GetMapping("/internal/auth/users/{id}")
    CreateUserDTO getUserById(@PathVariable("id") Long id);
}
