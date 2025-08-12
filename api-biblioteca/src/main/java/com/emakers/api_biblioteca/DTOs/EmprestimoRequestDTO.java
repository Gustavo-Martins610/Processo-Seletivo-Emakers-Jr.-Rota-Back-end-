package com.emakers.api_biblioteca.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(description = "DTO para criação de um novo empréstimo")
public record EmprestimoRequestDTO(
    @Schema(description = "ID da pessoa que está pegando o livro emprestado", example = "1")
    Long idEmprestimo,
    @Schema(description = "ID da pessoa a pegar o livro", example = "1")
    Long idPessoa,
    @Schema(description = "ID do livro a ser emprestado", example = "1")
    Long idLivro,
    @Schema(description = "Nome da pessoa a pegar o livro", example = "João da silva")
    String nomepessoa,
    @Schema(description = "Nome do livro a ser emprestado", example = "Dom Casmurro")
    String nomelivro,
    @Schema(description = "Data de início do empréstimo", example = "2025-07-27")
    LocalDate dataEmprestimo,
    @Schema(description = "Data de devolução do livro", example = "2025-08-10")
    LocalDate dataDevolucao,
    String status
    
) {
}
