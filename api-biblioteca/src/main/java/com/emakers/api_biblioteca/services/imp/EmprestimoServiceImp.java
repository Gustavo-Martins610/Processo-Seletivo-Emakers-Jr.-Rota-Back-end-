package com.emakers.api_biblioteca.services.imp;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emakers.api_biblioteca.DTOs.EmprestimoRequestDTO;
import com.emakers.api_biblioteca.DTOs.EmprestimoResponseDTO;
import com.emakers.api_biblioteca.models.EmprestimoModel;
import com.emakers.api_biblioteca.models.LivroModel;
import com.emakers.api_biblioteca.models.PessoaModel;
import com.emakers.api_biblioteca.repositories.EmprestimoRepository;
import com.emakers.api_biblioteca.repositories.LivroRepository;
import com.emakers.api_biblioteca.repositories.PessoaRepository;
import com.emakers.api_biblioteca.services.EmprestimoService;

@Service
public class EmprestimoServiceImp implements EmprestimoService{

    private static int Limite_Por_Pessoa = 3;

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

    long emprestimosAtivos = emprestimoRepository.countByPessoaIdPessoaAndDataDevolucaoIsNull(pessoa.getIdPessoa());
        if (emprestimosAtivos >= Limite_Por_Pessoa) {
        throw new IllegalStateException("Limite de empréstimos atingido para esta pessoa.");
        }

    
    if (livro.getQuantidade() <= 0) {
        throw new IllegalStateException("Livro não disponível para empréstimo");
    }


    // Cria o empréstimo
    EmprestimoModel emprestimo = new EmprestimoModel();
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
    public EmprestimoResponseDTO devolverLivro(Long idEmprestimo) {
        EmprestimoModel emprestimo = emprestimoRepository.findById(idEmprestimo)
            .orElseThrow(() -> new IllegalArgumentException("Empréstimo não encontrado"));

        if (emprestimo.getDataDevolucao() != null) {
            throw new IllegalStateException("Este livro já foi devolvido.");
        }

        emprestimo.setDataDevolucao(LocalDate.now());
        emprestimoRepository.save(emprestimo);

        LivroModel livro = emprestimo.getLivro();
        livro.setQuantidade(livro.getQuantidade() + 1);
        livroRepository.save(livro);

        return new EmprestimoResponseDTO(emprestimo);
    }

    @Override
    public List<EmprestimoResponseDTO> listarEmprestimosAtivosPorPessoa(Long idPessoa) {
    List<EmprestimoModel> emprestimos = emprestimoRepository
        .findByPessoaIdPessoaAndDataDevolucaoIsNull(idPessoa);

    return emprestimos.stream()
        .map(EmprestimoResponseDTO::new)
        .toList();
}
}
