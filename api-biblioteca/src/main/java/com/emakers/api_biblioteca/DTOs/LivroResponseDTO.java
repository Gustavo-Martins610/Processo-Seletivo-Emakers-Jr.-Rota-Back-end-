package com.emakers.api_biblioteca.DTOs;

import java.util.Date;

import com.emakers.api_biblioteca.models.LivroModel;


public record LivroResponseDTO(

    Long idLivro,
    String nome,
    String autor, 
    Date data_lancamento

) {
    public LivroResponseDTO(LivroModel livro){
        this(livro.getIdLivro(),livro.getNome(),livro.getAutor(),livro.getData_lancamento());
    }
}
