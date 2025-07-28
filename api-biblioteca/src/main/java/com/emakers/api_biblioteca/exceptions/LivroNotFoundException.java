package com.emakers.api_biblioteca.exceptions;

public class LivroNotFoundException extends RuntimeException {
    public LivroNotFoundException(Long id) {
        super("Livro com ID " + id + " não encontrado.");
    }
}
