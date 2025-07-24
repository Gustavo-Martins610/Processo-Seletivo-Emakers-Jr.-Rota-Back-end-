package com.emakers.api_biblioteca.DTOs;

import java.time.LocalDate;

import com.emakers.api_biblioteca.models.EmprestimoModel;


public record EmprestimoResponseDTO(

    Long idEmprestimo,
    Long idPessoa,
    Long idLivro,
    String nomepessoa,
    String nomelivro,
    LocalDate dataEmprestimo,
    LocalDate dataDevolucao

) {
    public EmprestimoResponseDTO(EmprestimoModel emprestimo){
        this(
        emprestimo.getIdEmprestimo(),
        emprestimo.getPessoa().getIdPessoa(),
        emprestimo.getLivro().getIdLivro(),
        emprestimo.getPessoa().getNome(),
        emprestimo.getLivro().getNome(),
        emprestimo.getDataEmprestimo(),
        emprestimo.getDataDevolucao()
        );
    }
}
