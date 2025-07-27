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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emakers.api_biblioteca.DTOs.LivroRequestDTO;
import com.emakers.api_biblioteca.DTOs.LivroResponseDTO;
import com.emakers.api_biblioteca.repositories.LivroRepository;
import com.emakers.api_biblioteca.services.LivroService;


import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

@Tag(name = "Livros", description = "Operações relacionadas a livros da biblioteca")
@RestController
@RequestMapping("/livro")
public class LivroController {

@Autowired
LivroRepository LivroRepository;
    @Autowired
    private LivroService livroService;


    @Operation(
        summary = "Listar todos os livros",
        description = "Retorna uma lista de todos os livros cadastrados."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso", content = @Content(schema = @Schema(implementation = LivroResponseDTO.class))),
    })
    @GetMapping(value = "/all")
    public ResponseEntity<List<LivroResponseDTO>> pegartodoslivros(){
        return ResponseEntity.status(HttpStatus.OK).body(livroService.pegartodoslivros());
    }


    @Operation(
        summary = "Buscar livro por ID",
        description = "Retorna as informações de um livro específico pelo seu ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livro encontrado", content = @Content(schema = @Schema(implementation = LivroResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado")  
    })
    @GetMapping(value = "/{idLivro}")
    public ResponseEntity<LivroResponseDTO> pegarlivroporid(@Parameter(description = "ID do livro") @PathVariable("idLivro") Long idlivro){
         return ResponseEntity.status(HttpStatus.OK).body(livroService.pegarlivroporid(idlivro));
    }


    @Operation(
        summary = "Cadastrar um novo livro",
        description = "Adiciona um novo livro ao acervo da biblioteca."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Livro cadastrado com sucesso", content = @Content(schema = @Schema(implementation = LivroResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida"),
        @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @PostMapping(value = "/create")
    public ResponseEntity<LivroResponseDTO> salvarlivro(@Valid @RequestBody LivroRequestDTO livroRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.salvarlivro(livroRequestDTO));
    }


    @Operation(summary = "Atualizar uma pessoa")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Livro atualizado"),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado"),
        @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @PostMapping(value = "/update")
    public ResponseEntity <LivroResponseDTO> mudarnomelivro(@Valid @PathVariable @Parameter(description = "ID do livro", example = "1") Long idlivro
    ,@Valid @RequestBody @Parameter(description = "Dados atualizados do Livro") LivroRequestDTO livroRequestDTO){
        return ResponseEntity.status(HttpStatus.OK).body(livroService.mudarnomelivro(idlivro,livroRequestDTO));
    }


    @Operation(
        summary = "Deletar livro",
        description = "Remove um livro do acervo da biblioteca pelo ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Livro removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado"),
        @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @DeleteMapping(value = "/delete/{idLivro}")
    public ResponseEntity <String> deletarlivro(@Parameter(description = "ID do livro a ser removido") @PathVariable("idLivro") Long idlivro){
        return ResponseEntity.status(HttpStatus.OK).body(livroService.deletarlivro(idlivro));
    }

}

