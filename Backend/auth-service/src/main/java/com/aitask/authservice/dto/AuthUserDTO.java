package com.aitask.authservice.dto;

public record AuthUserDTO(
        Long id,
        String username,
        String email
) {}
