package com.emakers.api_biblioteca.Testes;

import com.emakers.api_biblioteca.DTOs.LivroRequestDTO;
import com.emakers.api_biblioteca.DTOs.LivroResponseDTO;
import com.emakers.api_biblioteca.exceptions.LivroNotFoundException;
import com.emakers.api_biblioteca.models.LivroModel;
import com.emakers.api_biblioteca.repositories.LivroRepository;
import com.emakers.api_biblioteca.services.LivroService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
    void deveRetornarTodosOsLivros() {
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
    void deveRetornarLivroQuandoIdExistir() {
        LivroModel livro = new LivroModel();
        livro.setIdLivro(1L);
        livro.setNome("Livro Teste");

        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));

        LivroResponseDTO result = livroService.pegarlivroporid(1L);

        assertNotNull(result);
        assertEquals("Livro Teste", result.nome());
    }


    @Test
    void deveSalvarLivroCorretamente() {
        LivroRequestDTO dto = new LivroRequestDTO("Livro Novo", "Autor", LocalDate.of(2020, 1, 1), 5,"Disponível");

        LivroModel livroSalvo = new LivroModel(dto);
        livroSalvo.setIdLivro(10L);

        when(livroRepository.save(any(LivroModel.class))).thenReturn(livroSalvo);

        LivroResponseDTO result = livroService.salvarlivro(dto);

        assertNotNull(result);
        assertEquals("Livro Novo", result.nome());
    }

    @Test
    void deveAtualizarLivroQuandoIdExistir() {
        LivroModel livro = new LivroModel();
        livro.setIdLivro(2L);
        livro.setNome("Antigo");

        LivroRequestDTO dto = new LivroRequestDTO("Novo Nome", "Autor", LocalDate.now(), 2,"Disponível");

        when(livroRepository.findById(2L)).thenReturn(Optional.of(livro));
        when(livroRepository.save(any(LivroModel.class))).thenReturn(livro);

        LivroResponseDTO result = livroService.atualizarlivro(2L, dto);

        assertEquals("Novo Nome", result.nome());
    }

    @Test
    void deveLancarExcecaoAoAtualizarLivroInexistente() {
        LivroRequestDTO dto = new LivroRequestDTO("Nome", "Autor", LocalDate.now(), 1,"Disponível");
        when(livroRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(LivroNotFoundException.class, () -> livroService.atualizarlivro(99L, dto));
    }

    @Test
    void deveDeletarLivroQuandoIdExistir() {
        LivroModel livro = new LivroModel();
        livro.setIdLivro(3L);

        when(livroRepository.findById(3L)).thenReturn(Optional.of(livro));
        doNothing().when(livroRepository).delete(livro);

        String result = livroService.deletarlivro(3L);

        assertEquals("Livro de ID 3 deletado", result);
        verify(livroRepository).delete(livro);
    }

}
