package com.emakers.api_biblioteca.Testes;

import com.emakers.api_biblioteca.DTOs.PessoaRequestDTO;
import com.emakers.api_biblioteca.DTOs.PessoaResponseDTO;
import com.emakers.api_biblioteca.DTOs.PessoaUpdateDTO;
import com.emakers.api_biblioteca.models.PessoaModel;
import com.emakers.api_biblioteca.repositories.PessoaRepository;
import com.emakers.api_biblioteca.services.PessoaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PessoaServiceTest {

    @InjectMocks
    private PessoaService pessoaService;

    @Mock
    private PessoaRepository pessoaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPegartodaspessoas() {
        PessoaModel pessoa1 = new PessoaModel();
        pessoa1.setNome("João");
        PessoaModel pessoa2 = new PessoaModel();
        pessoa2.setNome("Maria");
        when(pessoaRepository.findAll()).thenReturn(Arrays.asList(pessoa1, pessoa2));

        List<PessoaResponseDTO> resultado = pessoaService.pegartodaspessoas();

        assertEquals(2, resultado.size());
        assertEquals("João", resultado.get(0).nome());
        assertEquals("Maria", resultado.get(1).nome());
    }

    @Test
    void testPegarpessoaporid_Success() {
        PessoaModel pessoa = new PessoaModel();
        pessoa.setIdPessoa(1L);
        pessoa.setNome("Carlos");
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));

        PessoaResponseDTO resultado = pessoaService.pegarpessoaporid(1L);

        assertEquals("Carlos", resultado.nome());
    }

    @Test
    void testPegarpessoaporid_NotFound() {
        when(pessoaRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                pessoaService.pegarpessoaporid(99L));

        assertEquals("ID não encontrado", exception.getMessage());
    }

    @Test
    void testSalvarpessoa() {
        PessoaRequestDTO dto = new PessoaRequestDTO(1L,"Ana","123.456.789-10","37300-247","analuisa@gmail.com", "123456789", "980", "AP202", "Rua candeias","São José", "Lavras", "MG");
        PessoaModel pessoa = new PessoaModel(dto);
        pessoa.setNome("Ana");

        when(pessoaRepository.save(any(PessoaModel.class))).thenReturn(pessoa);

        PessoaResponseDTO resultado = pessoaService.salvarpessoa(dto);

        assertEquals("Ana", resultado.nome());
    }

    @Test
    void testMudarnomepessoa_Success() {
        PessoaUpdateDTO dto = new PessoaUpdateDTO("Roberta","123.456.789-10","37300-247","roberta@gmail.com", "123456789", "980", "AP202");
        PessoaModel pessoa = new PessoaModel();
        pessoa.setIdPessoa(1L);
        pessoa.setNome("Roberto");
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
        when(pessoaRepository.save(any(PessoaModel.class))).thenReturn(pessoa);

        PessoaResponseDTO resultado = pessoaService.atualizarpessoa(1L, dto);

        assertEquals("Roberta", resultado.nome());
    }

    @Test
    void testDeletarpessoa_Success() {
        PessoaModel pessoa = new PessoaModel();
        pessoa.setIdPessoa(2L);
        pessoa.setNome("Thiago");
        when(pessoaRepository.findById(2L)).thenReturn(Optional.of(pessoa));
        doNothing().when(pessoaRepository).delete(pessoa);

        String retorno = pessoaService.deletarpessoa(2L);

        assertTrue(retorno.contains("2"));
        verify(pessoaRepository, times(1)).delete(pessoa);
    }
}
