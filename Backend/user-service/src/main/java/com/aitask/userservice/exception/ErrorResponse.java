package com.aitask.userservice.exception;


import lombok.Getter;

@Getter
public class ErrorResponse {

    private final int status;
    private final String error;
    private final String message;
    private final String path;
    private final String timestamp;

    public ErrorResponse(int status, String error, String message, String path, String timestamp) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = timestamp;
    }
}
