package com.aitask.apigateway.config;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class GatewayPublicKeyConfig {

    @Value("${auth.service.url:http://localhost:8081}")
    private String authServiceUrl;

    @Bean
    @Lazy   // ⭐ THIS LINE FIXES STARTUP CRASH
    public PublicKey authPublicKey() throws Exception {
        System.out.println("GATEWAY → Fetching public key from AUTH service...");

        WebClient client = WebClient.builder()
                .baseUrl(authServiceUrl)
                .build();

        String keyBase64 = client.get()
                .uri("/auth/public-key")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        byte[] decoded = Base64.getDecoder().decode(keyBase64);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
        KeyFactory kf = KeyFactory.getInstance("RSA");

        return kf.generatePublic(spec);
    }

}
