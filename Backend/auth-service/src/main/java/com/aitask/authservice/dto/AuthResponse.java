package com.aitask.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
	private Long id;
    private String name;
    private String email;
    private String role;
    private String token; // JWT token
}
