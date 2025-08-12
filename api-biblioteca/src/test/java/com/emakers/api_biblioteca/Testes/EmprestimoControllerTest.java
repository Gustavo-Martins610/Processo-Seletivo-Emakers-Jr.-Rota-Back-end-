package com.emakers.api_biblioteca.Testes;

import com.emakers.api_biblioteca.DTOs.EmprestimoRequestDTO;
import com.emakers.api_biblioteca.DTOs.EmprestimoResponseDTO;
import com.emakers.api_biblioteca.controllers.EmprestimoController;
import com.emakers.api_biblioteca.exceptions.EmprestimoNotFoundException;
import com.emakers.api_biblioteca.exceptions.PessoaNotFoundException;
import com.emakers.api_biblioteca.exceptions.ValidationException;
import com.emakers.api_biblioteca.services.EmprestimoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmprestimoControllerTest {

    @Mock
    private EmprestimoService emprestimoService;

    @InjectMocks
    private EmprestimoController emprestimoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEmprestarLivro_Sucesso() {
        EmprestimoRequestDTO req = new EmprestimoRequestDTO(1L, 2L, 3L, "Fulano", "Dom Casmurro",
                LocalDate.of(2025, 7, 27), null, "Ativo");
        EmprestimoResponseDTO resp = new EmprestimoResponseDTO(1L, 2L, 3L, "Fulano", "Dom Casmurro",
                LocalDate.of(2025, 7, 27), null, "Ativo");

        when(emprestimoService.emprestarLivro(any(EmprestimoRequestDTO.class))).thenReturn(resp);

        ResponseEntity<EmprestimoResponseDTO> response = emprestimoController.emprestarLivro(req);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Fulano", response.getBody().nomepessoa());
        assertEquals("Dom Casmurro", response.getBody().nomelivro());
    }

    @Test
    void testEmprestarLivro_PessoaNotFound() {
        EmprestimoRequestDTO req = new EmprestimoRequestDTO(1L, 99L, 3L, "Fulano", "Dom Casmurro",
                LocalDate.of(2025, 7, 27), null, "Ativo");

        when(emprestimoService.emprestarLivro(any())).thenThrow(new PessoaNotFoundException("Pessoa não encontrada"));

        assertThrows(PessoaNotFoundException.class, () -> emprestimoController.emprestarLivro(req));
    }

    @Test
    void testEmprestarLivro_ValidationException() {
        EmprestimoRequestDTO req = new EmprestimoRequestDTO(1L, 2L, 3L, "Fulano", "Dom Casmurro",
                LocalDate.of(2025, 7, 27), null, "Ativo");

        when(emprestimoService.emprestarLivro(any())).thenThrow(new ValidationException("Regra de negócio"));

        assertThrows(ValidationException.class, () -> emprestimoController.emprestarLivro(req));
    }

    @Test
    void testDevolverLivro_Sucesso() {
        Long idEmprestimo = 1L;
        EmprestimoResponseDTO resp = new EmprestimoResponseDTO(idEmprestimo, 2L, 3L, "Fulano", "Dom Casmurro",
                LocalDate.of(2025, 7, 27), LocalDate.of(2025, 8, 1), "Ativo");

        when(emprestimoService.devolverLivro(idEmprestimo)).thenReturn(resp);

        ResponseEntity<EmprestimoResponseDTO> response = emprestimoController.devolverLivro(idEmprestimo);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(idEmprestimo, response.getBody().idEmprestimo());
        assertEquals(LocalDate.of(2025, 8, 1), response.getBody().dataDevolucao());
    }

    @Test
    void testDevolverLivro_NotFound() {
        Long idEmprestimo = 99L;

        when(emprestimoService.devolverLivro(idEmprestimo))
                .thenThrow(new EmprestimoNotFoundException("Não encontrado"));

        assertThrows(EmprestimoNotFoundException.class, () -> emprestimoController.devolverLivro(idEmprestimo));
    }

    @Test
    void testListarEmprestimosAtivosPorPessoa_Sucesso() {
        Long idPessoa = 2L;
        EmprestimoResponseDTO resp = new EmprestimoResponseDTO(1L, idPessoa, 3L, "Fulano", "Dom Casmurro",
                LocalDate.of(2025, 7, 27), null, "Ativo");

        when(emprestimoService.listarEmprestimosAtivosPorPessoa(idPessoa)).thenReturn(List.of(resp));

        ResponseEntity<List<EmprestimoResponseDTO>> response = emprestimoController.listarEmprestimosAtivosPorPessoa(idPessoa);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
        assertEquals("Fulano", response.getBody().get(0).nomepessoa());
    }

    @Test
    void testListarEmprestimosAtivosPorPessoa_PessoaNotFound() {
        Long idPessoa = 999L;

        when(emprestimoService.listarEmprestimosAtivosPorPessoa(idPessoa))
                .thenThrow(new PessoaNotFoundException("Pessoa não encontrada"));

        assertThrows(PessoaNotFoundException.class, () ->
                emprestimoController.listarEmprestimosAtivosPorPessoa(idPessoa));
    }
}
