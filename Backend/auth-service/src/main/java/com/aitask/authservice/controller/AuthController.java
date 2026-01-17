package com.aitask.authservice.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aitask.authservice.dto.AuthRequest;
import com.aitask.authservice.dto.AuthResponse;
import com.aitask.authservice.dto.LoginRequest;
import com.aitask.authservice.model.UserAuth;
import com.aitask.authservice.repository.UserRepository;
import com.aitask.authservice.service.UserService;
import com.aitask.authservice.util.JwtProvider;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final UserRepository userRepository;

    // ✔ FIXED: Register should accept AuthRequest, not AuthResponse
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthRequest req) {
        UserAuth u = userService.register(req.getUsername(), req.getPassword(), req.getEmail());
        return ResponseEntity.ok(u.getUsername());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
    	
    	System.out.println("AUTH-SERVICE → login() called");
        System.out.println("Username: " + req.getIdentifier());
        // Authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		req.getIdentifier(),
                        req.getPassword()
                )
        );

        // Load user from DB
        UserAuth user = userRepository.findByUsername(req.getIdentifier())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Generate token with roles
        String token = jwtProvider.generateToken(user);

        System.out.println("Login user + token sends	" + token);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(new AuthResponse(token));

    }
    
    
    


}
