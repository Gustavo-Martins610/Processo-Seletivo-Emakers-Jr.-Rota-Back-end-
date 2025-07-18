package com.emakers.api_biblioteca.DTOs;

import java.time.LocalDate;


import com.emakers.api_biblioteca.models.LivroModel;


public record LivroResponseDTO(

    Long idLivro,
    String nome,
    String autor, 
    LocalDate data_lancamento,
    Integer quantidade

) {
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
