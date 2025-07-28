package com.emakers.api_biblioteca.exceptions;

public class PessoaNotFoundException extends RuntimeException {
    public PessoaNotFoundException(Long id) {
        super("Pessoa com ID " + id + " não encontrada.");
    }
}