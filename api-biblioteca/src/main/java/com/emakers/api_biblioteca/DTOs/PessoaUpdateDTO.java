package com.emakers.api_biblioteca.DTOs;

public record PessoaUpdateDTO(
    String nome,
    String cpf,
    String cep,
    String email,
    String senha,
    String numero,
    String complemento
){}

