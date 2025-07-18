package com.emakers.api_biblioteca.DTOs;

import java.time.LocalDate;


import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;

public record LivroRequestDTO(
    @NotBlank(message = "Nome é necessário")
    String nome,

    @NotBlank(message = "Autor é necessário")
    String autor, 
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate data_lancamento,

    Integer quantidade
) {
}
