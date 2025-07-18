package com.emakers.api_biblioteca.models;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "Emprestimo")
public class EmprestimoModel {

    @EmbeddedId
    private EmprestimoId id;

    @ManyToOne
    @MapsId("idPessoa")
    @JoinColumn(name = "idPessoa")
    private PessoaModel pessoa;

    @ManyToOne
    @MapsId("idLivro")
    @JoinColumn(name = "idLivro")
    private LivroModel livro;

    @Column(nullable = false)
    private LocalDate dataEmprestimo;

    @Column(nullable = false)
    private LocalDate dataDevolucao;

    
    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    // Getters e setters
    public EmprestimoId getId() {
        return id;
    }

    public void setId(EmprestimoId id) {
        this.id = id;
    }

    public PessoaModel getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaModel pessoa) {
        this.pessoa = pessoa;
    }

    public LivroModel getLivro() {
        return livro;
    }

    public void setLivro(LivroModel livro) {
        this.livro = livro;
    }
}

