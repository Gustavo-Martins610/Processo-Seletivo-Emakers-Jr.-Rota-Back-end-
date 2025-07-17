package com.emakers.api_biblioteca.DTOs;

import com.emakers.api_biblioteca.models.PessoaModel;

public record PessoaResponseDTO(
    Long idPessoa,
    String nome,
    String cpf,
    String cep, 
    String email,
    String senha

) {
     public PessoaResponseDTO(PessoaModel pessoa){
        this(pessoa.getIdPessoa(),pessoa.getNome(),pessoa.getCpf(),pessoa.getCep(), pessoa.getEmail(),pessoa.getSenha());
    }
}
