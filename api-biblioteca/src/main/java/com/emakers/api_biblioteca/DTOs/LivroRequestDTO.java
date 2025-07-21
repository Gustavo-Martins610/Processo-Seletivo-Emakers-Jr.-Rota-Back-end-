package com.emakers.api_biblioteca.DTOs;

import java.time.LocalDate;


public record LivroRequestDTO(
    String nome,
    String autor, 
    LocalDate data_lancamento,
    Integer quantidade
) {
}
