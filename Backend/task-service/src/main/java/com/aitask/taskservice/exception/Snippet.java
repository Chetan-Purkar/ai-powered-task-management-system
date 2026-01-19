package com.aitask.taskservice.exception;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

public class Snippet {
	@ExceptionHandler(feign.FeignException.class)
	public ResponseEntity<ErrorResponse> handleFeignException(
	        feign.FeignException ex,
	        HttpServletRequest request) {
	
	    return ResponseEntity.status(ex.status())
	            .body(new ErrorResponse(
	                    ex.status(),
	                    "DOWNSTREAM_SERVICE_ERROR",
	                    ex.getMessage(),
	                    request.getRequestURI(),
	                    LocalDateTime.now().toString()
	            ));
	}
	
}

