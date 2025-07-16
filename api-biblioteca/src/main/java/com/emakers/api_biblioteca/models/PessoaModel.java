package com.emakers.api_biblioteca.models;

import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "Pessoa")



public class PessoaModel implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID idPessoa;
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


    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    public UUID getIdPessoa() {
        return idPessoa;
    }
    public void setIdPessoa(UUID idPessoa) {
        this.idPessoa = idPessoa;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

}
