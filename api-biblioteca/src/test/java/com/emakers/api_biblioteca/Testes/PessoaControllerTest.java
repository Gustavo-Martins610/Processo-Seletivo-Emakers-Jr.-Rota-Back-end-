package com.emakers.api_biblioteca.Testes;

import com.emakers.api_biblioteca.DTOs.PessoaRequestDTO;
import com.emakers.api_biblioteca.DTOs.PessoaResponseDTO;
import com.emakers.api_biblioteca.DTOs.PessoaUpdateDTO;
import com.emakers.api_biblioteca.DTOs.ViaCepResponseDTO;
import com.emakers.api_biblioteca.controllers.PessoaController;
import com.emakers.api_biblioteca.exceptions.PessoaNotFoundException;
import com.emakers.api_biblioteca.exceptions.ValidationException;
import com.emakers.api_biblioteca.models.PessoaModel;
import com.emakers.api_biblioteca.Users.UserRole;
import com.emakers.api_biblioteca.repositories.PessoaRepository;
import com.emakers.api_biblioteca.services.PessoaService;
import com.emakers.api_biblioteca.services.ViaCepService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.springframework.http.ResponseEntity;

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
        PessoaResponseDTO pessoa1 = new PessoaResponseDTO(1L,"Fulano", "12345678901", "35500-200", "novo@email.com", "123456789", "123", "AP202", "rua martins", "São José", "Lavras", "MG", UserRole.USER);
        PessoaResponseDTO pessoa2 = new PessoaResponseDTO(2L,"Beltrano", "12345678901", "35500-200", "novo@email.com", "123456789", "123", "AP202", "rua martins", "São José", "Lavras", "MG", UserRole.USER);

        when(pessoaService.pegartodaspessoas()).thenReturn(Arrays.asList(pessoa1, pessoa2));

        ResponseEntity<List<PessoaResponseDTO>> response = pessoaController.pegartodaspessoas();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        assertEquals("Fulano", response.getBody().get(0).nome());
    }

    @Test
    void testPegarPessoaPorId_Sucesso() {
        Long id = 1L;
        PessoaResponseDTO dto = new PessoaResponseDTO(id,"Fulano", "12345678901", "35500-200", "novo@email.com", "123456789", "123", "AP202", "rua martins", "São José", "Lavras", "MG", UserRole.USER);

        when(pessoaService.pegarpessoaporid(id)).thenReturn(dto);

        ResponseEntity<PessoaResponseDTO> response = pessoaController.pegarpessoaporid(id);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Fulano", response.getBody().nome());
    }

    @Test
    void testPegarPessoaPorId_NotFound() {
        Long id = 10L;
        when(pessoaService.pegarpessoaporid(id)).thenThrow(new PessoaNotFoundException("Pessoa não encontrada"));

        assertThrows(PessoaNotFoundException.class, () -> {
            pessoaController.pegarpessoaporid(id);
        });
    }

    @Test
    void testSalvarPessoa_Sucesso() {
        PessoaRequestDTO req = new PessoaRequestDTO("Fulano", "12345678901", "35501-248", "novo@email.com", "123456789", "123", "AP202", "rua martins", "São José", "Lavras", "MG");
        PessoaModel pessoaModel = new PessoaModel();
        pessoaModel.setNome("Fulano");
        pessoaModel.setEmail("fulano@email.com");
        pessoaModel.setCpf("12345678901");
        pessoaModel.setCep("12345-000");
        pessoaModel.setSenha("senha");
        pessoaModel.setRole(UserRole.USER);
        pessoaModel.setNumero("123");
        pessoaModel.setComplemento("apto");

        ViaCepResponseDTO viaCep = new ViaCepResponseDTO("25500-256","Rua candeias","Centro", "Lavras", "MG");

        when(pessoaRepository.findByEmail("novo@email.com")).thenReturn(null);
        when(viaCepService.consultarCep("35501-248")).thenReturn(viaCep);

        ResponseEntity<PessoaResponseDTO> response = pessoaController.salvarpessoa(req);

        assertEquals(201, response.getStatusCode().value());
        assertEquals("Fulano", response.getBody().nome());
        verify(pessoaRepository).save(any(PessoaModel.class));
    }

    @Test
    void testSalvarPessoa_EmailJaCadastrado() {
        PessoaRequestDTO req = new PessoaRequestDTO("Fulano", "12345678901", "35500-200", "novo@email.com", "123456789", "123", "AP202", "rua martins", "São José", "Lavras", "MG");
        when(pessoaRepository.findByEmail("novo@email.com")).thenReturn(new PessoaModel());

        assertThrows(ValidationException.class, () -> {
            pessoaController.salvarpessoa(req);
        });
    }

    @Test
    void testMudarnomePessoa_Sucesso() {
        Long id = 1L;
        PessoaUpdateDTO req = new PessoaUpdateDTO("Nome", "12345678901", "35500-200", "novo@email.com", "123456789", "123", "AP202");
        PessoaResponseDTO resp = new PessoaResponseDTO(id,"NovoNome", "12345678901", "35500-200", "novo@email.com", "123456789", "123", "AP202", "rua martins", "São José", "Lavras", "MG", UserRole.USER);

        when(pessoaService.atualizarpessoa(eq(id), any(PessoaUpdateDTO.class))).thenReturn(resp);

        ResponseEntity<PessoaResponseDTO> response = pessoaController.atualizarpessoa(id, req);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("NovoNome", response.getBody().nome());
    }

    @Test
    void testMudarnomePessoa_NotFound() {
        Long id = 99L;
        PessoaUpdateDTO req = new PessoaUpdateDTO("Fulano", "12345678901", "35500-200", "novo@email.com", "123456789", "123", "AP202");
        when(pessoaService.atualizarpessoa(eq(id), any(PessoaUpdateDTO.class)))
                .thenThrow(new PessoaNotFoundException("Pessoa não encontrada"));

        assertThrows(PessoaNotFoundException.class, () -> {
            pessoaController.atualizarpessoa(id, req);
        });
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
        Long id = 123L;
        doThrow(new PessoaNotFoundException("Pessoa não encontrada"))
                .when(pessoaService).deletarpessoa(id);

        assertThrows(PessoaNotFoundException.class, () -> {
            pessoaController.deletarpessoa(id);
        });
    }
}
