package com.emakers.api_biblioteca.services.imp;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emakers.api_biblioteca.DTOs.EmprestimoRequestDTO;
import com.emakers.api_biblioteca.DTOs.EmprestimoResponseDTO;
import com.emakers.api_biblioteca.models.EmprestimoId;
import com.emakers.api_biblioteca.models.EmprestimoModel;
import com.emakers.api_biblioteca.models.LivroModel;
import com.emakers.api_biblioteca.models.PessoaModel;
import com.emakers.api_biblioteca.repositories.EmprestimoRepository;
import com.emakers.api_biblioteca.repositories.LivroRepository;
import com.emakers.api_biblioteca.repositories.PessoaRepository;
import com.emakers.api_biblioteca.services.EmprestimoService;

@Service
public class EmprestimoServiceImp implements EmprestimoService{

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public EmprestimoResponseDTO emprestarLivro (EmprestimoRequestDTO emprestimorequestDTO) {
    LivroModel livro = livroRepository.findById(emprestimorequestDTO.idLivro())
        .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));

    PessoaModel pessoa = pessoaRepository.findById(emprestimorequestDTO.idPessoa())
        .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));

    
    if (livro.getQuantidade() <= 0) {
        throw new IllegalStateException("Livro não disponível para empréstimo");
    }

    // Monta a chave composta
    EmprestimoId id = new EmprestimoId();
    id.setIdPessoa(pessoa.getIdPessoa());
    id.setIdLivro(livro.getIdLivro());

    // Cria o empréstimo
    EmprestimoModel emprestimo = new EmprestimoModel();
    emprestimo.setId(id);
    emprestimo.setPessoa(pessoa);
    emprestimo.setLivro(livro);
    emprestimo.setDataEmprestimo(LocalDate.now());
    emprestimo.setDataDevolucao(null);


    livro.setQuantidade(livro.getQuantidade() - 1);
    livroRepository.save(livro);

    emprestimoRepository.save(emprestimo);

    return new EmprestimoResponseDTO(emprestimo);
}

    @Override
    public EmprestimoResponseDTO devolverLivro(Long idPessoa, Long idLivro) {
    EmprestimoId id = new EmprestimoId();
    id.setIdPessoa(idPessoa);
    id.setIdLivro(idLivro);

    EmprestimoModel emprestimo = emprestimoRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Empréstimo não encontrado"));

    if (emprestimo.getDataDevolucao() != null) {
        throw new IllegalStateException("Este livro já foi devolvido.");
    }

    // Define data de devolução
    emprestimo.setDataDevolucao(LocalDate.now());
    emprestimoRepository.save(emprestimo);

    // Atualiza quantidade de livros disponíveis
    LivroModel livro = emprestimo.getLivro();
    livro.setQuantidade(livro.getQuantidade() + 1);
    livroRepository.save(livro);

    return new EmprestimoResponseDTO(emprestimo);
}
}
