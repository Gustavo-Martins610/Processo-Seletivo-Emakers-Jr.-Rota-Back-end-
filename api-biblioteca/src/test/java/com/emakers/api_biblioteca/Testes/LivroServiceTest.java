package com.emakers.api_biblioteca.Testes;

import com.emakers.api_biblioteca.DTOs.LivroRequestDTO;
import com.emakers.api_biblioteca.DTOs.LivroResponseDTO;
import com.emakers.api_biblioteca.models.LivroModel;
import com.emakers.api_biblioteca.repositories.LivroRepository;
import com.emakers.api_biblioteca.services.LivroService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;
import javax.management.RuntimeErrorException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LivroServiceTest {

    @InjectMocks
    private LivroService livroService;

    @Mock
    private LivroRepository livroRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPegartodoslivros() {
        LivroModel livro1 = new LivroModel();
        livro1.setIdLivro(1L);
        livro1.setNome("Livro 1");

        LivroModel livro2 = new LivroModel();
        livro2.setIdLivro(2L);
        livro2.setNome("Livro 2");

        when(livroRepository.findAll()).thenReturn(Arrays.asList(livro1, livro2));

        List<LivroResponseDTO> result = livroService.pegartodoslivros();
        assertEquals(2, result.size());
        assertEquals("Livro 1", result.get(0).nome());
        assertEquals("Livro 2", result.get(1).nome());
    }

    @Test
    void testPegarlivroporid_Success() {
        LivroModel livro = new LivroModel();
        livro.setIdLivro(1L);
        livro.setNome("Livro Teste");

        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));

        LivroResponseDTO result = livroService.pegarlivroporid(1L);
        assertNotNull(result);
        assertEquals("Livro Teste", result.nome());
    }

    @Test
    void testPegarlivroporid_NotFound() {
        when(livroRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeErrorException.class, () -> livroService.pegarlivroporid(1L));
    }

    @Test
    void testSalvarlivro() {
        LivroRequestDTO dto = mock(LivroRequestDTO.class);
        when(dto.nome()).thenReturn("Livro Novo");

        LivroModel livroSalvo = new LivroModel(dto);
        livroSalvo.setIdLivro(10L);

        when(livroRepository.save(any(LivroModel.class))).thenReturn(livroSalvo);

        LivroResponseDTO result = livroService.salvarlivro(dto);
        assertNotNull(result);
        assertEquals("Livro Novo", result.nome());
    }

    @Test
    void testMudarnomelivro_Success() {
        LivroModel livro = new LivroModel();
        livro.setIdLivro(2L);
        livro.setNome("Antigo");

        LivroRequestDTO dto = mock(LivroRequestDTO.class);
        when(dto.nome()).thenReturn("Novo Nome");

        when(livroRepository.findById(2L)).thenReturn(Optional.of(livro));
        when(livroRepository.save(any(LivroModel.class))).thenReturn(livro);

        LivroResponseDTO result = livroService.mudarnomelivro(2L, dto);

        assertEquals("Novo Nome", result.nome());
    }

    @Test
    void testMudarnomelivro_NotFound() {
        LivroRequestDTO dto = mock(LivroRequestDTO.class);
        when(livroRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeErrorException.class, () -> livroService.mudarnomelivro(99L, dto));
    }

    @Test
    void testDeletarlivro_Success() {
        LivroModel livro = new LivroModel();
        livro.setIdLivro(3L);
        livro.setNome("Livro 3");

        when(livroRepository.findById(3L)).thenReturn(Optional.of(livro));
        doNothing().when(livroRepository).delete(livro);

        String result = livroService.deletarlivro(3L);
        assertEquals("Livro de ID 3 deletado", result);
        verify(livroRepository).delete(livro);
    }

    @Test
    void testDeletarlivro_NotFound() {
        when(livroRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> livroService.deletarlivro(99L));
    }
}
