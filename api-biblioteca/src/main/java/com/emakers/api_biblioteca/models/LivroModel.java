package com.emakers.api_biblioteca.models;

import java.io.Serializable;
import java.util.Date;


import com.emakers.api_biblioteca.DTOs.LivroRequestDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "Livro")
@NoArgsConstructor

public class LivroModel implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLivro;
    @Column(length = 100, nullable = false) //define o tamanho e a restrição do campo
    private String nome;
    @Column(length = 100, nullable = false)
    private String autor;
    @Temporal(TemporalType.DATE) // indica que o campo será armazenado como DATE (sem as horas como é padrão do Java.util)
    private Date data_lancamento;


    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    public Long getIdLivro() {
        return idLivro;
    }
    public void setIdLivro(Long idLivro) {
        this.idLivro = idLivro;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }
    public Date getData_lancamento() {
        return data_lancamento;
    }
    public void setData_lancamento(Date data_lancamento) {
        this.data_lancamento = data_lancamento;
    }

    @Builder
    public LivroModel(LivroRequestDTO livroRequestDTO){
        this.nome = livroRequestDTO.nome();
        this.autor = livroRequestDTO.autor();
        this.data_lancamento = livroRequestDTO.data_lancamento();
    }


}
