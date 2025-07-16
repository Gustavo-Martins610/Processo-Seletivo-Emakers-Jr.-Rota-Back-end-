package com.emakers.api_biblioteca.models;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity
@Table(name = "Livro")

public class LivroModel implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID idLivro;
    @Column(length = 100, nullable = false) //define o tamanho e a restrição do campo
    private String nome;
    @Column(length = 100, nullable = false)
    private String autor;
    @Temporal(TemporalType.DATE) // indica que o campo será armazenado como DATE (sem as horas como é padrão do Java.util)
    private Date data_lancamento;


    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    public UUID getIdLivro() {
        return idLivro;
    }
    public void setIdLivro(UUID idLivro) {
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

}
