package com.emakers.api_biblioteca.DTOs;

import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;
import com.emakers.api_biblioteca.models.EmprestimoModel;

@Schema(description = "DTO de resposta com os dados de um empréstimo realizado")
public record EmprestimoResponseDTO(
    @Schema(description = "ID do empréstimo", example = "10")
    Long idEmprestimo,
    @Schema(description = "ID da pessoa que está pegando o livro emprestado", example = "1")
    Long idPessoa,
    @Schema(description = "ID do livro a ser emprestado", example = "1")
    Long idLivro,
    @Schema(description = "Nome da pessoa que fez o empréstimo", example = "Marcos Silva")
    String nomepessoa,
    @Schema(description = "Nome do livro emprestado", example = "O Pequeno Príncipe")
    String nomelivro,
    @Schema(description = "Data de início do empréstimo", example = "2025-07-27")
    LocalDate dataEmprestimo,
    @Schema(description = "Data de devolução do livro", example = "2025-08-10")
    LocalDate dataDevolucao,
    String status

) {
    public EmprestimoResponseDTO(EmprestimoModel emprestimo){
        this(
        emprestimo.getIdEmprestimo(),
        emprestimo.getPessoa().getIdPessoa(),
        emprestimo.getLivro().getIdLivro(),
        emprestimo.getPessoa().getNome(),
        emprestimo.getLivro().getNome(),
        emprestimo.getDataEmprestimo(),
        emprestimo.getDataDevolucao(),
        emprestimo.getStatus()
        );
    }
}
