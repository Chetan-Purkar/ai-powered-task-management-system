package com.aitask.adminservice.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.aitask.adminservice.dto.ErrorResponse;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ---------- 403 FORBIDDEN ----------
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handle403(
            AccessDeniedException ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(
                        403,
                        "FORBIDDEN",
                        "You are not allowed to access this resource",
                        request.getRequestURI(),
                        LocalDateTime.now()
                ));
    }

    // ---------- 401 UNAUTHORIZED ----------
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handle401(
            AuthenticationException ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(
                        401,
                        "UNAUTHORIZED",
                        "Authentication required",
                        request.getRequestURI(),
                        LocalDateTime.now()
                ));
    }

    // ---------- 404 ----------
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle404(
            EntityNotFoundException ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        404,
                        "NOT_FOUND",
                        ex.getMessage(),
                        request.getRequestURI(),
                        LocalDateTime.now()
                ));
    }

    // ---------- 400 ----------
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handle400(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        400,
                        "BAD_REQUEST",
                        ex.getMessage(),
                        request.getRequestURI(),
                        LocalDateTime.now()
                ));
    }

    // ---------- 500 ----------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle500(
            Exception ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        500,
                        "INTERNAL_SERVER_ERROR",
                        ex.getMessage(),
                        request.getRequestURI(),
                        LocalDateTime.now()
                ));
    }
}
