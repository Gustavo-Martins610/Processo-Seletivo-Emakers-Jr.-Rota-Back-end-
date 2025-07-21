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
import com.fasterxml.jackson.annotation.JsonProperty;

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
    @Column(length = 20, nullable = false)
    private String cpf;
    @Column(length = 9)
    private String cep;
    @Column(length = 100)
    private String email;
    @Column(length = 100)
    private String senha;
    @Column(length = 8)
    private String numero;
    @Column(length = 100)
    private String logradouro;
    @Column(length = 50)
    private String bairro;
    @Column(length = 50)
    @JsonProperty("localidade")
    private String cidade;
    @Column(length = 2)
    @JsonProperty("uf")
    private String estado;


    @Builder
    public PessoaModel(PessoaRequestDTO pessoaRequestDTO){
        this.nome = pessoaRequestDTO.nome();
        this.cpf = pessoaRequestDTO.cpf();
        this.cep = pessoaRequestDTO.cep();
        this.numero = pessoaRequestDTO.numero();
        this.email = pessoaRequestDTO.email();
        this.senha = pessoaRequestDTO.senha();
        this.logradouro = pessoaRequestDTO.logradouro();
        this.bairro = pessoaRequestDTO.bairro();
        this.cidade = pessoaRequestDTO.cidade();
        this.estado = pessoaRequestDTO.estado();
    }
}
