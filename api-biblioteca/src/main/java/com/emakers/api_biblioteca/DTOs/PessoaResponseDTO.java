package com.emakers.api_biblioteca.DTOs;

import com.emakers.api_biblioteca.models.PessoaModel;

import lombok.Builder;

public record PessoaResponseDTO(
    Long idPessoa,
    String nome,
    String cpf,
    String cep, 
    String email,
    String senha,
    String numero,
    String logradouro,
    String bairro,
    String cidade,
    String estado

) {
    @Builder
     public PessoaResponseDTO(PessoaModel pessoa){
        this(
        pessoa.getIdPessoa(),
        pessoa.getNome(),
        pessoa.getCpf(),
        pessoa.getCep(),
        pessoa.getNumero(),
        pessoa.getEmail(),
        pessoa.getSenha(),
        pessoa.getLogradouro(),
        pessoa.getBairro(),
        pessoa.getCidade(),
        pessoa.getEstado()
        );
    }
}
