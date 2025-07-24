package com.emakers.api_biblioteca.models;


import java.time.LocalDate;



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
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "Livro")
@NoArgsConstructor

public class LivroModel{
   
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLivro;
    @Column(length = 100, nullable = false)
    private String nome;
    @Column(length = 100, nullable = false)
    private String autor;
    @Temporal(TemporalType.DATE)
    private LocalDate data_lancamento;
    @Column(nullable = false)
    private Integer quantidade;


    @Builder
    public LivroModel(LivroRequestDTO livroRequestDTO){
        this.nome = livroRequestDTO.nome();
        this.autor = livroRequestDTO.autor();
        this.data_lancamento = livroRequestDTO.data_lancamento();
        this.quantidade = livroRequestDTO.quantidade();
    }


}
