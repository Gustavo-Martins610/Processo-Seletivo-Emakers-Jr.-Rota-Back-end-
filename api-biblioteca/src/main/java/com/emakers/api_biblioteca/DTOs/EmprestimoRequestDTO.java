package com.emakers.api_biblioteca.DTOs;

import java.time.LocalDate;

public record EmprestimoRequestDTO(
    Long idPessoa,
    Long idLivro,
    String nomepessoa,
    String nomelivro,
    LocalDate dataEmprestimo,
    LocalDate dataDevolucao
) {
    

}
