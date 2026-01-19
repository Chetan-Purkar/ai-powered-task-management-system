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

        String identifier = req.getIdentifier();

        // Authenticate (Spring Security)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        identifier,
                        req.getPassword()
                )
        );

        // Fetch user by username OR email
        UserAuth user = userRepository.findByUsernameOrEmail(identifier, identifier)
                .orElseThrow(() ->
                        new jakarta.persistence.EntityNotFoundException(
                            "User not found with username/email: " + identifier
                        )
                );

        // Generate JWT
        String token = jwtProvider.generateToken(user);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(new AuthResponse(token));
    }

    
    


}
