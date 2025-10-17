package com.aitask.authservice.controller;

import com.aitask.authservice.dto.AuthRequest;
import com.aitask.authservice.dto.AuthResponse;
import com.aitask.authservice.model.User;
import com.aitask.authservice.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }

    @GetMapping("/validate")
    public boolean validateToken(@RequestParam String token) {
        return authService.validateToken(token);
    }
}
