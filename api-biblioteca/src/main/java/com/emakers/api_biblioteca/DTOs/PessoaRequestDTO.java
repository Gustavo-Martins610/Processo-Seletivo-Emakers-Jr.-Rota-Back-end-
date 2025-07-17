package com.emakers.api_biblioteca.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PessoaRequestDTO(
    @NotBlank(message = "Nome é necessário")
    String nome,

    @NotBlank(message = "CPF é necesário")
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "CPF inválido (formato esperado: 000.000.000-00)")
    String cpf,

    @NotBlank(message = "CEP é necesário")
    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "CEP inválido (formato esperado: 00000-000)")
    String cep,

    @NotBlank(message = "E-mail é necessário")
    @Email(message = "O E-mail fornecido é inválido")
    String email,

    @NotBlank(message = "Senha é necessária")
    String senha


) {
}
