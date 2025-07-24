package com.emakers.api_biblioteca.DTOs;

import com.emakers.api_biblioteca.Users.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record PessoaRequestDTO(
    
    String nome,
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "CPF inválido (formato esperado: 000.000.000-00)")
    String cpf,
    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "CEP inválido (formato esperado: 00000-000)")
    String cep,
    String numero,
    @Email(message = "O E-mail fornecido é inválido")
    String email,
    String senha,
    String logradouro,
    String bairro,
    String cidade,
    String estado,
    UserRole role
) {
}
