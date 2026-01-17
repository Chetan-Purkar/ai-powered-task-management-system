package com.aitask.taskservice.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.aitask.taskservice.config.FeignConfig;
import com.aitask.taskservice.dto.TaskRequestDTO;


@FeignClient(name = "auth-service", configuration = FeignConfig.class)
public interface AuthServiceClient {

    @GetMapping("/internal/auth/users/{id}")
    TaskRequestDTO getUserById(@PathVariable("id") Long id);
}
