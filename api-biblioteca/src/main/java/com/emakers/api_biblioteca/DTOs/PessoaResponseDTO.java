package com.emakers.api_biblioteca.DTOs;



public record PessoaResponseDTO(
    Long idPessoa,
    String nome,
    String cpf,
    String cep, 
    String email,
    String senha

) {
}
