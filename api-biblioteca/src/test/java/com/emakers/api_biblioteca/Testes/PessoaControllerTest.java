package com.emakers.api_biblioteca.Testes;

import com.emakers.api_biblioteca.DTOs.*;
import com.emakers.api_biblioteca.Enums.UserRole;
import com.emakers.api_biblioteca.controllers.PessoaController;
import com.emakers.api_biblioteca.exceptions.PessoaNotFoundException;
import com.emakers.api_biblioteca.exceptions.ValidationException;
import com.emakers.api_biblioteca.models.PessoaModel;
import com.emakers.api_biblioteca.repositories.PessoaRepository;
import com.emakers.api_biblioteca.services.PessoaService;
import com.emakers.api_biblioteca.services.ViaCepService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PessoaControllerTest {

    private PessoaService pessoaService;
    private ViaCepService viaCepService;
    private PessoaRepository pessoaRepository;
    private PessoaController pessoaController;

    @BeforeEach
    void setUp() {
        pessoaService = mock(PessoaService.class);
        viaCepService = mock(ViaCepService.class);
        pessoaRepository = mock(PessoaRepository.class);

        pessoaController = new PessoaController();
        pessoaController.setPessoaService(pessoaService);
        pessoaController.setViaCepService(viaCepService);
        pessoaController.setPessoaRepository(pessoaRepository);
    }

    @Test
    void testPegarTodasPessoas_Sucesso() {
        PessoaResponseDTO pessoa1 = new PessoaResponseDTO(1L,"Fulano", "12345678901", "35500-200", "novo@email.com", "123456789", "123", "AP202", "Rua A", "Centro", "Cidade", "MG", UserRole.USER);
        PessoaResponseDTO pessoa2 = new PessoaResponseDTO(2L,"Beltrano", "98765432100", "35500-300", "beltrano@email.com", "987654321", "456", "AP203", "Rua B", "Bairro", "Cidade", "MG", UserRole.USER);

        when(pessoaService.pegartodaspessoas()).thenReturn(Arrays.asList(pessoa1, pessoa2));

        ResponseEntity<List<PessoaResponseDTO>> response = pessoaController.pegartodaspessoas();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Fulano", response.getBody().get(0).nome());
    }

    @Test
    void testPegarPessoaPorId_Sucesso() {
        Long id = 1L;
        PessoaResponseDTO dto = new PessoaResponseDTO(id,"Fulano", "12345678901", "35500-200", "novo@email.com", "123456789", "123", "AP202", "Rua A", "Centro", "Cidade", "MG", UserRole.USER);

        when(pessoaService.pegarpessoaporid(id)).thenReturn(dto);

        ResponseEntity<PessoaResponseDTO> response = pessoaController.pegarpessoaporid(id);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Fulano", response.getBody().nome());
    }

    @Test
    void testPegarPessoaPorId_NotFound() {
        Long id = 99L;
        when(pessoaService.pegarpessoaporid(id)).thenThrow(new PessoaNotFoundException("Pessoa não encontrada"));

        assertThrows(PessoaNotFoundException.class, () -> pessoaController.pegarpessoaporid(id));
    }

    @Test
    void testSalvarPessoa_EmailJaCadastrado() {
        PessoaRequestDTO req = new PessoaRequestDTO(1L, "Fulano", "12345678901", "35501-248", "novo@email.com", "123456789", "123", "AP202", "Rua A", "Centro", "Cidade", "MG");
        when(pessoaRepository.findByEmail("novo@email.com")).thenReturn(new PessoaModel());

        assertThrows(ValidationException.class, () -> pessoaController.salvarpessoa(req));
    }

    @Test
    void testAtualizarPessoa_Sucesso() {
        Long id = 1L;
        PessoaUpdateDTO req = new PessoaUpdateDTO("Fulano Atualizado", "12345678901", "35500-200", "email@atualizado.com", "123456789", "321", "AP999");
        PessoaResponseDTO resp = new PessoaResponseDTO(id,"Fulano Atualizado", "12345678901", "35500-200", "email@atualizado.com", "123456789", "321", "AP999", "Rua A", "Centro", "Cidade", "MG", UserRole.USER);

        when(pessoaService.atualizarpessoa(eq(id), any(PessoaUpdateDTO.class))).thenReturn(resp);

        ResponseEntity<PessoaResponseDTO> response = pessoaController.atualizarpessoa(id, req);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Fulano Atualizado", response.getBody().nome());
    }

    @Test
    void testAtualizarPessoa_NotFound() {
        Long id = 99L;
        PessoaUpdateDTO req = new PessoaUpdateDTO("Fulano", "12345678901", "35500-200", "email@email.com", "123456789", "123", "AP202");

        when(pessoaService.atualizarpessoa(eq(id), any(PessoaUpdateDTO.class)))
                .thenThrow(new PessoaNotFoundException("Pessoa não encontrada"));

        assertThrows(PessoaNotFoundException.class, () -> pessoaController.atualizarpessoa(id, req));
    }

    @Test
    void testDeletarPessoa_Sucesso() {
        Long id = 1L;
        ResponseEntity<Void> response = pessoaController.deletarpessoa(id);

        assertEquals(204, response.getStatusCode().value());
        verify(pessoaService).deletarpessoa(id);
    }

    @Test
    void testDeletarPessoa_NotFound() {
        Long id = 999L;
        doThrow(new PessoaNotFoundException("Pessoa não encontrada"))
                .when(pessoaService).deletarpessoa(id);

        assertThrows(PessoaNotFoundException.class, () -> pessoaController.deletarpessoa(id));
    }
}
