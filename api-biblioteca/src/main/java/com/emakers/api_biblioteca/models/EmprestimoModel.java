package com.emakers.api_biblioteca.models;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Emprestimo")
public class EmprestimoModel {

    @EmbeddedId
    private EmprestimoId id;

    @ManyToOne
    @MapsId("idPessoa")
    @JoinColumn(name = "id_pessoa")
    private PessoaModel pessoa;

    @ManyToOne
    @MapsId("idLivro")
    @JoinColumn(name = "id_livro")
    private LivroModel livro;

    @Column(name = "data_emprestimo")
    private LocalDate dataEmprestimo;

    @Column(name = "data_devolucao")
    private LocalDate dataDevolucao;

}

