package com.emakers.api_biblioteca.DTOs;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;

public record LivroRequestDTO(
    @NotBlank(message = "Nome é necessário")
    String nome,

    @NotBlank(message = "Autor é necessário")
    String autor, 
    
    Date data_lancamento
) {
}
