package com.emakers.api_biblioteca.services;

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

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Serviço responsável pelas regras de negócio relacionadas a empréstimos de livros.
 */
@Tag(name = "Serviço de Empréstimos", description = "Contém as regras de negócio para empréstimos e devoluções de livros.")
@Service
public class EmprestimoService {

    private static final int LIMITE_POR_PESSOA = 3;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    /**
     * Realiza um empréstimo de livro para uma pessoa, respeitando regras de disponibilidade e limite.
     *
     * @param emprestimorequestDTO DTO com os dados necessários para o empréstimo
     * @return DTO contendo os dados do empréstimo realizado
     * @throws IllegalArgumentException se o livro ou a pessoa não forem encontrados
     * @throws IllegalStateException se o livro não estiver disponível ou a pessoa tiver atingido o limite de empréstimos
     */
    public EmprestimoResponseDTO emprestarLivro(EmprestimoRequestDTO emprestimorequestDTO) {
        LivroModel livro = livroRepository.findById(emprestimorequestDTO.idLivro())
            .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));

        PessoaModel pessoa = pessoaRepository.findById(emprestimorequestDTO.idPessoa())
            .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));

        long emprestimosAtivos = emprestimoRepository.countByPessoaIdPessoaAndDataDevolucaoIsNull(pessoa.getIdPessoa());
        if (emprestimosAtivos >= LIMITE_POR_PESSOA) {
            throw new IllegalStateException("Limite de empréstimos atingido para esta pessoa.");
        }

        if (livro.getQuantidade() <= 0) {
            throw new IllegalStateException("Livro não disponível para empréstimo");
        }

        EmprestimoModel emprestimo = new EmprestimoModel();
        emprestimo.setPessoa(pessoa);
        emprestimo.setLivro(livro);
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setDataDevolucao(null);
        emprestimo.setStatus("Vigente");

        livro.setQuantidade(livro.getQuantidade() - 1);
        if (livro.getQuantidade() == 0){
        livro.setStatus("Indisponível");
        }
        livroRepository.save(livro);

        emprestimoRepository.save(emprestimo);

        return new EmprestimoResponseDTO(emprestimo);
    }

    /**
     * Realiza a devolução de um livro emprestado.
     *
     * @param idEmprestimo ID do empréstimo a ser devolvido
     * @return DTO com os dados do empréstimo após devolução
     * @throws IllegalArgumentException se o empréstimo não for encontrado
     * @throws IllegalStateException se o livro já tiver sido devolvido anteriormente
     */
    public EmprestimoResponseDTO devolverLivro(Long idEmprestimo) {
        EmprestimoModel emprestimo = emprestimoRepository.findById(idEmprestimo)
            .orElseThrow(() -> new IllegalArgumentException("Empréstimo não encontrado"));

        if (emprestimo.getDataDevolucao() != null) {
            throw new IllegalStateException("Este livro já foi devolvido.");
        }

        emprestimo.setDataDevolucao(LocalDate.now());
        emprestimo.setStatus("Devolvido");
        emprestimoRepository.save(emprestimo);

        LivroModel livro = emprestimo.getLivro();
        livro.setQuantidade(livro.getQuantidade() + 1);
        if (livro.getQuantidade() > 0){
        livro.setStatus("Disponível");
        }
        livroRepository.save(livro);

        return new EmprestimoResponseDTO(emprestimo);
    }

    /**
     * Lista todos os empréstimos ativos (ainda não devolvidos) de uma pessoa.
     *
     * @param idPessoa ID da pessoa a ser consultada
     * @return Lista de DTOs dos empréstimos ativos
     */
    public List<EmprestimoResponseDTO> listarEmprestimosAtivosPorPessoa(Long idPessoa) {
        List<EmprestimoModel> emprestimos = emprestimoRepository.findByPessoaIdPessoaAndDataDevolucaoIsNull(idPessoa);
        return emprestimos.stream().map(EmprestimoResponseDTO::new).toList();
    }

    /**
     * Lista todos os empréstimos devolvidos de uma pessoa.
     *
     * @param idPessoa ID da pessoa
     * @param status Status do empréstimo (ex: "Devolvido")
     * @return Lista de DTOs de empréstimos com o status informado
     */
    public List<EmprestimoResponseDTO> listarEmprestimosDevolvidos(Long idPessoa, String status) {
        List<EmprestimoModel> emprestimos = emprestimoRepository.findByPessoa_IdPessoaAndStatus(idPessoa, status);
        return emprestimos.stream().map(EmprestimoResponseDTO::new).toList();
    }
}
