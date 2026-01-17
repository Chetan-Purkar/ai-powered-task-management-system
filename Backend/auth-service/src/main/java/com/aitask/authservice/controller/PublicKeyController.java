package com.aitask.authservice.controller;

import com.aitask.authservice.util.JwtProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
public class PublicKeyController {
    private final JwtProvider jwtProvider;

    public PublicKeyController(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @GetMapping("/auth/public-key")
    public String getPublicKeyBase64() {
        byte[] encoded = jwtProvider.getPublicKey().getEncoded();
        System.out.println("auth public-key sends");
        return Base64.getEncoder().encodeToString(encoded);
    }
}
