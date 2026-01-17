package com.aitask.apigateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;

import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
@Component
public class JwtAuthGlobalFilter implements GlobalFilter, Ordered {

    private final PublicKey publicKey;

    public JwtAuthGlobalFilter(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();
        
        System.out.println("Incomming Request - " + path);

        if (path.startsWith("/auth")
        	    || path.startsWith("/actuator")
        	    || path.startsWith("/internal")) {

        	 return chain.filter(exchange);
        }


        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        	exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        	byte[] bytes = "Unauthorized".getBytes(StandardCharsets.UTF_8);
        	DataBuffer buffer = exchange.getResponse()
        	        .bufferFactory()
        	        .wrap(bytes);

        	return exchange.getResponse().writeWith(Mono.just(buffer));

        }
        
        String forwardedFor =
                exchange.getRequest()
                        .getHeaders()
                        .getFirst("X-Forwarded-For");

        System.out.println("X-Forwarded-For: " + forwardedFor);


        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(authHeader.substring(7))
                    .getBody();

            ServerHttpRequest mutated = exchange.getRequest().mutate()
                    .header("X-User-Id", String.valueOf(claims.get("userId", Long.class)))
                    .header("X-Username", claims.getSubject())
                    .header("X-Roles", claims.get("role", String.class))
                    .build();

            System.out.println("Method: " + exchange.getRequest().getMethod());
            System.out.println("Full URL: " + exchange.getRequest().getURI());

            return chain.filter(exchange.mutate().request(mutated).build());

        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            System.out.println("Api getway - Error Throw");
            return exchange.getResponse().setComplete();
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
