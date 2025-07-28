package com.emakers.api_biblioteca.exceptions;

public class EmprestimoNotFoundException extends RuntimeException {
    public EmprestimoNotFoundException(String id) {
        super("Empréstimo com ID " + id + " não encontrado.");
    }
}
