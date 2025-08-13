package com.emakers.api_biblioteca.Testes;

import com.emakers.api_biblioteca.DTOs.LivroRequestDTO;
import com.emakers.api_biblioteca.DTOs.LivroResponseDTO;
import com.emakers.api_biblioteca.controllers.LivroController;
import com.emakers.api_biblioteca.exceptions.LivroNotFoundException;
import com.emakers.api_biblioteca.services.LivroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

class LivroControllerUnitTest {

    private LivroService livroService;
    private LivroController livroController;

    @BeforeEach
    void setUp() {
        livroService = Mockito.mock(LivroService.class);
        livroController = new LivroController();
        // Injeta o mock manualmente
        java.lang.reflect.Field field;
        try {
            field = LivroController.class.getDeclaredField("livroService");
            field.setAccessible(true);
            field.set(livroController, livroService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testPegarTodosLivros() {
        LivroResponseDTO livro1 = new LivroResponseDTO(1L, "Dom Casmurro", "Machado de Assis", LocalDate.of(1899, 2, 1), 10,"Disponível");
        LivroResponseDTO livro2 = new LivroResponseDTO(2L, "Memórias Póstumas", "Machado de Assis", LocalDate.of(1881, 1, 1), 8,"Disponível");
        Mockito.when(livroService.pegartodoslivros()).thenReturn(Arrays.asList(livro1, livro2));

        ResponseEntity<List<LivroResponseDTO>> resp = livroController.pegartodoslivros();

        assertEquals(2, resp.getBody().size());
        assertEquals("Dom Casmurro", resp.getBody().get(0).nome());
    }

    @Test
    void testPegarlivroporid_Sucesso() {
        Long idLivro = 1L;
        LivroResponseDTO livro = new LivroResponseDTO(idLivro, "Dom Casmurro", "Machado de Assis", LocalDate.of(1899, 2, 1), 10,"Disponível");

        Mockito.when(livroService.pegarlivroporid(idLivro)).thenReturn(livro);

        ResponseEntity<LivroResponseDTO> resp = livroController.pegarlivroporid(idLivro);

        assertEquals("Dom Casmurro", resp.getBody().nome());
        assertEquals(200, resp.getStatusCode().value());
    }

    @Test
    void testPegarlivroporid_NotFound() {
        Long idLivro = 999L;
        Mockito.when(livroService.pegarlivroporid(idLivro)).thenThrow(new LivroNotFoundException("Livro não encontrado"));
        assertThrows(LivroNotFoundException.class, () -> livroController.pegarlivroporid(idLivro));
    }

    @Test
    void testSalvarLivro() {
        LivroRequestDTO request = new LivroRequestDTO("Dom Casmurro", "Machado de Assis", LocalDate.of(1899, 2, 1), 10,"Disponível");
        LivroResponseDTO response = new LivroResponseDTO(1L, "Dom Casmurro", "Machado de Assis", LocalDate.of(1899, 2, 1), 10,"Disponível");

        Mockito.when(livroService.salvarlivro(any(LivroRequestDTO.class))).thenReturn(response);

        ResponseEntity<LivroResponseDTO> resp = livroController.salvarlivro(request);

        assertEquals(201, resp.getStatusCode().value());
        assertEquals("Dom Casmurro", resp.getBody().nome());
    }

    @Test
    void testMudarnomelivro_Sucesso() {
        Long idLivro = 1L;
        LivroRequestDTO request = new LivroRequestDTO("Novo Nome", "Machado de Assis", LocalDate.of(1899, 2, 1), 15,"Disponível");
        LivroResponseDTO response = new LivroResponseDTO(idLivro, "Novo Nome", "Machado de Assis", LocalDate.of(1899, 2, 1), 15,"Disponível");

        Mockito.when(livroService.atualizarlivro(eq(idLivro), any(LivroRequestDTO.class))).thenReturn(response);

        ResponseEntity<LivroResponseDTO> resp = livroController.mudarnomelivro(idLivro, request);

        assertEquals("Novo Nome", resp.getBody().nome());
        assertEquals(200, resp.getStatusCode().value());
    }

    @Test
    void testMudarnomelivro_NotFound() {
        Long idLivro = 999L;
        LivroRequestDTO request = new LivroRequestDTO("Qualquer", "Machado de Assis", LocalDate.of(1899, 2, 1), 1,"Disponível");

        Mockito.when(livroService.atualizarlivro(eq(idLivro), any(LivroRequestDTO.class))).thenThrow(new LivroNotFoundException("Livro não encontrado"));

        assertThrows(LivroNotFoundException.class, () -> livroController.mudarnomelivro(idLivro, request));
    }

    @Test
    void testDeletarlivro_Sucesso() {
        Long idLivro = 1L;
        ResponseEntity<Void> resp = livroController.deletarlivro(idLivro);

        assertEquals(204, resp.getStatusCode().value());
    }

    @Test
    void testDeletarlivro_NotFound() {
        Long idLivro = 999L;
        Mockito.doThrow(new LivroNotFoundException("Livro não encontrado")).when(livroService).deletarlivro(idLivro);

        assertThrows(LivroNotFoundException.class, () -> livroController.deletarlivro(idLivro));
    }
}
