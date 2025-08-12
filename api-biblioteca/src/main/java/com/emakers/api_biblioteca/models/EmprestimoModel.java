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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_emprestimo")
    private Long idEmprestimo;


    @ManyToOne
    @JoinColumn(name = "id_pessoa")
    private PessoaModel pessoa;

    @ManyToOne
    @JoinColumn(name = "id_livro")
    private LivroModel livro;

    @Column(name = "data_emprestimo")
    private LocalDate dataEmprestimo;

    @Column(name = "data_devolucao")
    private LocalDate dataDevolucao;

    @Column(name = "Status")
    private String status;

}

