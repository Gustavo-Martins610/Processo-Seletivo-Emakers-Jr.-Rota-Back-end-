package com.emakers.api_biblioteca.DTOs;

import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para requisições de criação ou atualização de livros.")
public record LivroRequestDTO(
    @Schema(description = "Nome do livro.", example = "Dom Casmurro")
    String nome,
    @Schema(description = "Autor do livro.", example = "Machado de Assis")
    String autor, 
    @Schema(description = "Data de lançamento do livro no formato AAAA-MM-DD.", example = "1899-02-01")
    LocalDate data_lancamento,
    @Schema(description = "Quantidade do Livro na Biblioteca", example = "10")
    Integer quantidade
) {
}
