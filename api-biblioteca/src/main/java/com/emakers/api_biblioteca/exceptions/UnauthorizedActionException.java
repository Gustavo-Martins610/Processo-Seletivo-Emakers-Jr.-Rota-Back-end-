package com.emakers.api_biblioteca.exceptions;

public class UnauthorizedActionException extends RuntimeException {
    public UnauthorizedActionException(String action) {
        super("Ação não autorizada: " + action);
    }
}
