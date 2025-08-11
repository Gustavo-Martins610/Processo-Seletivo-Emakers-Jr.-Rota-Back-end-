package com.emakers.api_biblioteca.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import com.emakers.api_biblioteca.models.PessoaModel;
import com.emakers.api_biblioteca.Users.UserRole;

import lombok.Builder;

@Schema(description = "DTO de resposta com os dados de uma pessoa")
public record PessoaResponseDTO(

    @Schema(description = "ID único da pessoa", example = "1")
    Long idPessoa,
    @Schema(description = "Nome completo da pessoa", example = "João da Silva")
    String nome,
    @Schema(description = "CPF da pessoa", example = "123.456.789-00")
    String cpf,
    @Schema(description = "CEP da pessoa", example = "37200-000")
    String cep,
    @Schema(description = "E-mail da pessoa", example = "joao@email.com")
    String email,
    @Schema(description = "Senha da pessoa", example = "123456")
    String senha,
    @Schema(description = "Número do endereço", example = "100")
    String numero,
    @Schema(description = "Complemento do endereço", example = "Apto 203")
    String complemento,
    @Schema(description = "Rua/Avenida da pessoa", example = "Rua Candeias")
    String logradouro,
    @Schema(description = "Bairro da pessoa", example = "Centro")
    String bairro,
    @Schema(description = "Cidade da pessoa", example = "Lavras")
    String cidade,
    @Schema(description = "Estado da pessoa", example = "MG")
    String estado,
    @Schema(description = "Role da Pessoa para permitir/bloquear acessos", example = "ADMIN")
    UserRole role

    

) {
    @Builder
     public PessoaResponseDTO(PessoaModel pessoa){
        this(
        pessoa.getIdPessoa(),
        pessoa.getNome(),
        pessoa.getCpf(),
        pessoa.getCep(),
        pessoa.getEmail(),
        pessoa.getSenha(),
        pessoa.getNumero(),
        pessoa.getComplemento(),
        pessoa.getLogradouro(),
        pessoa.getBairro(),
        pessoa.getCidade(),
        pessoa.getEstado(),
        pessoa.getRole()
        );
    }

    public void setRole(UserRole admin) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setRole'");
    }
}
