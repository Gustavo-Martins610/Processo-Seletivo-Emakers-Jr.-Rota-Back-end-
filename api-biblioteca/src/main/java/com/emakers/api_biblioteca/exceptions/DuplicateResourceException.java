package com.emakers.api_biblioteca.exceptions;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String resource, String field) {
        super(resource + " com o campo '" + field + "' já existe.");
    }
}