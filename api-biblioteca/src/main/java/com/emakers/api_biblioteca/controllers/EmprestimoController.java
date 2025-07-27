package com.emakers.api_biblioteca.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.emakers.api_biblioteca.DTOs.EmprestimoRequestDTO;
import com.emakers.api_biblioteca.DTOs.EmprestimoResponseDTO;
import com.emakers.api_biblioteca.services.EmprestimoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/emprestimo")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;

    @Operation(
        summary = "Criar um novo empréstimo",
        description = "Realiza o empréstimo de um livro para uma pessoa."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Empréstimo realizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos para empréstimo")
    })
    @PostMapping(value = "/criar")
    public ResponseEntity<EmprestimoResponseDTO> emprestarLivro(@Parameter(description = "ID do empréstimo", example = "1") @Valid @RequestBody EmprestimoRequestDTO emprestimoRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(emprestimoService.emprestarLivro(emprestimoRequestDTO));
    }


    @Operation(
        summary = "Devolver um livro emprestado",
        description = "Registra a devolução de um livro a partir do ID do empréstimo."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Devolução registrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado")
    })
    @PostMapping(value = "/devolver/{idEmprestimo}")
    public ResponseEntity<EmprestimoResponseDTO> devolverLivro(@Parameter(description = "ID do empréstimo", example = "1") @PathVariable Long idEmprestimo) {
        return ResponseEntity.status(HttpStatus.OK).body(emprestimoService.devolverLivro(idEmprestimo));
    }


     @Operation(
        summary = "Listar empréstimos ativos por pessoa",
        description = "Retorna uma lista de empréstimos ativos para uma determinada pessoa."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de empréstimos ativos retornada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada"),
        @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @GetMapping("/pessoa/{idPessoa}/ativos")
    public ResponseEntity<List<EmprestimoResponseDTO>> listarEmprestimosAtivosPorPessoa(@Parameter(description = "ID da pessoa", example = "1") @PathVariable Long idPessoa) {  
    return ResponseEntity.ok(emprestimoService.listarEmprestimosAtivosPorPessoa(idPessoa));
    }
}