package com.emakers.api_biblioteca.controllers;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.emakers.api_biblioteca.DTOs.LivroRequestDTO;
import com.emakers.api_biblioteca.DTOs.LivroResponseDTO;
import com.emakers.api_biblioteca.exceptions.LivroNotFoundException;
import com.emakers.api_biblioteca.exceptions.ValidationException;
import com.emakers.api_biblioteca.services.LivroService;

import jakarta.validation.Valid;

@Tag(name = "Livros", description = "Operações relacionadas a livros da biblioteca")
@RestController
@RequestMapping("/livro")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @Operation(
        summary = "Listar todos os livros",
        description = "Retorna uma lista de todos os livros cadastrados."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso", content = @Content(schema = @Schema(implementation = LivroResponseDTO.class))),
    })
    @GetMapping("/all")
    public ResponseEntity<List<LivroResponseDTO>> pegartodoslivros() {
        return ResponseEntity.ok(livroService.pegartodoslivros());
    }

    @Operation(
        summary = "Buscar livro por ID",
        description = "Retorna as informações de um livro específico pelo seu ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livro encontrado", content = @Content(schema = @Schema(implementation = LivroResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado")  
    })
    @GetMapping("/{idLivro}")
    public ResponseEntity<LivroResponseDTO> pegarlivroporid(
            @Parameter(description = "ID do livro") @PathVariable("idLivro") Long idlivro) {
        try {
            LivroResponseDTO response = livroService.pegarlivroporid(idlivro);
            return ResponseEntity.ok(response);
        } catch (LivroNotFoundException ex) {
            throw ex;
        }
    }

    @Operation(
        summary = "Cadastrar um novo livro",
        description = "Adiciona um novo livro ao acervo da biblioteca."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Livro cadastrado com sucesso", content = @Content(schema = @Schema(implementation = LivroResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida"),
        @ApiResponse(responseCode = "409", description = "Livro já cadastrado")
    })
    @PostMapping("/create")
    public ResponseEntity<LivroResponseDTO> salvarlivro(
            @Valid @RequestBody LivroRequestDTO livroRequestDTO) {
        try {
            LivroResponseDTO response = livroService.salvarlivro(livroRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ValidationException ex) {
            throw ex;
        }
    }

    @Operation(
        summary = "Atualizar livro",
        description = "Atualiza as informações de um livro pelo ID."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Livro atualizado", content = @Content(schema = @Schema(implementation = LivroResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @PostMapping("/update/{idLivro}")
    public ResponseEntity<LivroResponseDTO> mudarnomelivro(
            @Parameter(description = "ID do livro", example = "1") @PathVariable Long idLivro,
            @Valid @RequestBody LivroRequestDTO livroRequestDTO) {
        try {
            LivroResponseDTO response = livroService.mudarnomelivro(idLivro, livroRequestDTO);
            return ResponseEntity.ok(response);
        } catch (LivroNotFoundException ex) {
            throw ex;
        } catch (ValidationException ex) {
            throw ex;
        }
    }

    @Operation(
        summary = "Deletar livro",
        description = "Remove um livro do acervo da biblioteca pelo ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Livro removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @DeleteMapping("/delete/{idLivro}")
    public ResponseEntity<Void> deletarlivro(
            @Parameter(description = "ID do livro a ser removido") @PathVariable("idLivro") Long idLivro) {
        try {
            livroService.deletarlivro(idLivro);
            return ResponseEntity.noContent().build();
        } catch (LivroNotFoundException ex) {
            throw ex;
        }
    }
}
