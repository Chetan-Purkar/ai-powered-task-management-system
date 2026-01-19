package com.aitask.apigateway.fallback;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aitask.apigateway.exception.ErrorResponse;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/task")
    public ResponseEntity<ErrorResponse> taskServiceFallback(ServerHttpRequest request) {

        ErrorResponse response = new ErrorResponse(
                503,
                "SERVICE_UNAVAILABLE",
                "Task Service is DOWN. Please try again later.",
                request.getURI().getPath(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
}
