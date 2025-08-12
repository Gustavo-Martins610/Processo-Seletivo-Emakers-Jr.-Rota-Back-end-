package com.emakers.api_biblioteca.Testes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import com.emakers.api_biblioteca.DTOs.EmprestimoRequestDTO;
import com.emakers.api_biblioteca.DTOs.EmprestimoResponseDTO;
import com.emakers.api_biblioteca.models.EmprestimoModel;
import com.emakers.api_biblioteca.models.LivroModel;
import com.emakers.api_biblioteca.models.PessoaModel;
import com.emakers.api_biblioteca.repositories.EmprestimoRepository;
import com.emakers.api_biblioteca.repositories.LivroRepository;
import com.emakers.api_biblioteca.repositories.PessoaRepository;
import com.emakers.api_biblioteca.services.EmprestimoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class EmprestimoServiceImpTest {

    @Mock
    private EmprestimoRepository emprestimoRepository;

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private EmprestimoService emprestimoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEmprestarLivro_Success() {
        long idLivro = 1L;
        long idPessoa = 1L;
        Long idEmprestimo = 1L;
        EmprestimoModel Emprestimo = new EmprestimoModel();
        Emprestimo.setIdEmprestimo(idEmprestimo);
        Emprestimo.setDataDevolucao(null);
        LivroModel livro = new LivroModel();
        livro.setIdLivro(idLivro);
        livro.setQuantidade(2);
        PessoaModel pessoa = new PessoaModel();
        pessoa.setIdPessoa(idPessoa);

        EmprestimoRequestDTO requestDTO = new EmprestimoRequestDTO(idEmprestimo, idLivro, idPessoa,"Gustavo",
        "Dom Casmurro",LocalDate.now(), LocalDate.now().plusDays(7),"Ativo");

        when(livroRepository.findById(idLivro)).thenReturn(Optional.of(livro));
        when(pessoaRepository.findById(idPessoa)).thenReturn(Optional.of(pessoa));
        when(emprestimoRepository.countByPessoaIdPessoaAndDataDevolucaoIsNull(idPessoa)).thenReturn(0L);

        
        EmprestimoResponseDTO response = emprestimoService.emprestarLivro(requestDTO);

        
        assertNotNull(response);
        assertEquals(idPessoa, response.idPessoa());
        assertEquals(idLivro, response.idLivro());
        verify(livroRepository).save(any(LivroModel.class));
        verify(emprestimoRepository).save(any(EmprestimoModel.class));
    }

    @Test
    void testDevolverLivro_Success() {
    long idEmprestimo = 2L;
    long idLivro = 2L;
    LivroModel livro = new LivroModel();
    livro.setIdLivro(idLivro);
    livro.setQuantidade(0);

    long idPessoa = 2L;
    PessoaModel pessoa = new PessoaModel();
    pessoa.setIdPessoa(idPessoa);

    EmprestimoModel emprestimo = new EmprestimoModel();
    emprestimo.setIdEmprestimo(idEmprestimo);
    emprestimo.setLivro(livro);
    emprestimo.setPessoa(pessoa);
    emprestimo.setDataDevolucao(null);

    when(emprestimoRepository.findById(idEmprestimo)).thenReturn(Optional.of(emprestimo));
    when(emprestimoRepository.save(any(EmprestimoModel.class))).thenReturn(emprestimo);
    when(livroRepository.save(any(LivroModel.class))).thenReturn(livro);

    EmprestimoResponseDTO response = emprestimoService.devolverLivro(idEmprestimo);

    assertNotNull(response);
    assertEquals(idEmprestimo, response.idEmprestimo());
    assertNotNull(emprestimo.getDataDevolucao());
}

    @Test
    void testListarEmprestimosAtivosPorPessoa() {
    long idPessoa = 1L;

    PessoaModel pessoa = new PessoaModel();
    pessoa.setIdPessoa(idPessoa);
    LivroModel livro = new LivroModel();
    livro.setIdLivro(123L);

    EmprestimoModel emprestimo = new EmprestimoModel();
    emprestimo.setPessoa(pessoa);
    emprestimo.setLivro(livro);

    when(emprestimoRepository.findByPessoaIdPessoaAndDataDevolucaoIsNull(idPessoa))
        .thenReturn(List.of(emprestimo));

    List<EmprestimoResponseDTO> result = emprestimoService.listarEmprestimosAtivosPorPessoa(idPessoa);

    assertNotNull(result);
    assertEquals(1, result.size());
    }
}
