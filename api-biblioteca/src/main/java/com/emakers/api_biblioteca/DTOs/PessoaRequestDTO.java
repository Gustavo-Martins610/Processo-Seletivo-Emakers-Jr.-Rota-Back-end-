package com.emakers.api_biblioteca.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para criação de uma nova pessoa")
public record PessoaRequestDTO(
    @Schema(description = "Nome completo da pessoa", example = "João da Silva")
    String nome,
    @Schema(description = "CPF da pessoa", example = "123.456.789-00")
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "CPF inválido (formato esperado: 000.000.000-00)")
    String cpf,
    @Schema(description = "CEP da pessoa", example = "37200-000")
    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "CEP inválido (formato esperado: 00000-000)")
    String cep,
    @Schema(description = "E-mail da pessoa", example = "joao@email.com")
    @Email(message = "O E-mail fornecido é inválido")
    String email,
    @Schema(description = "Senha da pessoa", example = "123456")
    String senha,
    @Schema(description = "Número do endereço", example = "100")
    String numero,
    @Schema(description = "Complemento do endereço", example = "Apto 203")
    String complemento,
    @Schema(description = "Rua/Avenida da pessoa", example = "Rua Candeias")
    String logradouro,
    @Schema(description = "Bairro da pessoa", example = "Centro")
    String bairro,
    @Schema(description = "Cidade da pessoa", example = "Lavras")
    String cidade,
    @Schema(description = "Estado da pessoa", example = "MG")
    String estado
) {
}
