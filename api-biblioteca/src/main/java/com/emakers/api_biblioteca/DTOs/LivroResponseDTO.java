package com.emakers.api_biblioteca.DTOs;

import java.time.LocalDate;


import com.emakers.api_biblioteca.models.LivroModel;
import io.swagger.v3.oas.annotations.media.Schema;


import lombok.Builder;

@Schema(description = "DTO de resposta com os dados de um livro.")
public record LivroResponseDTO(
    @Schema(description = "ID do livro.", example = "1")
    Long idLivro,
    @Schema(description = "Nome do livro.", example = "Dom Casmurro")
    String nome,
    @Schema(description = "Autor do livro.", example = "Machado de Assis")
    String autor,
    @Schema(description = "Data de lançamento do livro no formato AAAA-MM-DD.", example = "1899-02-01")
    LocalDate data_lancamento,
    @Schema(description = "Quantidade do Livro na Biblioteca", example = "10")
    Integer quantidade

) {
    @Builder
    public LivroResponseDTO(LivroModel livro){
        this(
        livro.getIdLivro(),
        livro.getNome(),
        livro.getAutor(),
        livro.getData_lancamento(),
        livro.getQuantidade()
        );
    }
}
