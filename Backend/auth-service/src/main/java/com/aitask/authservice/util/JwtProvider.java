package com.aitask.authservice.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import com.aitask.authservice.model.UserAuth;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

@Component
public class JwtProvider {

    private final KeyPair keyPair;

    public JwtProvider(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public String generateToken(UserAuth user) {

        PrivateKey privateKey = keyPair.getPrivate();
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .setSubject(user.getUsername())      // username
                .claim("userId", user.getId())       // IMPORTANT
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + 86400000)) // 1 day
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }
}
