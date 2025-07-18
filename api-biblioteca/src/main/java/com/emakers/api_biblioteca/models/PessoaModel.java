package com.emakers.api_biblioteca.models;

import jakarta.persistence.Table;
import lombok.Builder;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;



import com.emakers.api_biblioteca.DTOs.PessoaRequestDTO;

@Entity
@Getter
@Setter
@Table(name = "Pessoa")
@NoArgsConstructor


public class PessoaModel{
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPessoa;
    @Column(length = 100, nullable = false) //define o tamanho e a restrição do campo
    private String nome;
    @Column(length = 11, nullable = false)
    private String cpf;
    @Column(length = 9)
    private String cep;
    @Column(length = 100)
    private String email;
    @Column(length = 100)
    private String senha;


    @Builder
    public PessoaModel(PessoaRequestDTO pessoaRequestDTO){
        this.nome = pessoaRequestDTO.nome();
        this.cpf = pessoaRequestDTO.cpf();
        this.cep = pessoaRequestDTO.cep();
        this.email = pessoaRequestDTO.email();
        this.senha = pessoaRequestDTO.senha();
    }
}
