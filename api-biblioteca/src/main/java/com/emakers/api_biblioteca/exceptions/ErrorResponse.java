package com.emakers.api_biblioteca.exceptions;

import java.time.LocalDateTime;

import lombok.Data;



@Data
public class ErrorResponse {
    
    private int status;
    private String message;
    private LocalDateTime timestamp;
    private String path;
    private String error;

     public ErrorResponse(int status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

}
