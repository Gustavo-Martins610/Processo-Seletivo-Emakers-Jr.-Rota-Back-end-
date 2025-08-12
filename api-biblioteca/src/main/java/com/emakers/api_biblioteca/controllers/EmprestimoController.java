package com.emakers.api_biblioteca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.emakers.api_biblioteca.DTOs.EmprestimoRequestDTO;
import com.emakers.api_biblioteca.DTOs.EmprestimoResponseDTO;
import com.emakers.api_biblioteca.exceptions.EmprestimoNotFoundException;
import com.emakers.api_biblioteca.exceptions.PessoaNotFoundException;
import com.emakers.api_biblioteca.exceptions.ValidationException;
import com.emakers.api_biblioteca.models.PessoaModel;
import com.emakers.api_biblioteca.services.EmprestimoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Empréstimos", description = "Operações relacionadas ao empréstimo de livros da biblioteca")
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
        @ApiResponse(responseCode = "400", description = "Dados inválidos para empréstimo"),
        @ApiResponse(responseCode = "404", description = "Pessoa ou livro não encontrado"),
        @ApiResponse(responseCode = "409", description = "Recurso em conflito (ex: já emprestado)")
    })
    @PostMapping("/criar")
    public ResponseEntity<EmprestimoResponseDTO> emprestarLivro(
            @Parameter(description = "Dados do empréstimo") @Valid @RequestBody EmprestimoRequestDTO emprestimoRequestDTO) {
        try {
            EmprestimoResponseDTO response = emprestimoService.emprestarLivro(emprestimoRequestDTO);
            return ResponseEntity.ok(response);
        } catch (PessoaNotFoundException | ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ValidationException("Erro ao tentar realizar o empréstimo: " + ex.getMessage());
        }
    }

    @Operation(
        summary = "Devolver um livro emprestado",
        description = "Registra a devolução de um livro a partir do ID do empréstimo."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Devolução registrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado")
    })
    @PostMapping("/devolver/{idEmprestimo}")
    public ResponseEntity<EmprestimoResponseDTO> devolverLivro(
            @Parameter(description = "ID do empréstimo", example = "1") @PathVariable Long idEmprestimo) {
        try {
            EmprestimoResponseDTO response = emprestimoService.devolverLivro(idEmprestimo);
            return ResponseEntity.ok(response);
        } catch (EmprestimoNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ValidationException("Erro ao tentar devolver o livro: " + ex.getMessage());
        }
    }

    @Operation(
        summary = "Listar empréstimos ativos por pessoa (admin)",
        description = "Retorna uma lista de empréstimos ativos para uma determinada pessoa."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de empréstimos ativos retornada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada"),
        @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pessoa/{idPessoa}/ativos")
    public ResponseEntity<List<EmprestimoResponseDTO>> listarEmprestimosAtivosPorPessoa(
            @Parameter(description = "ID da pessoa", example = "1") @PathVariable Long idPessoa) {
        try {
            List<EmprestimoResponseDTO> response = emprestimoService.listarEmprestimosAtivosPorPessoa(idPessoa);
            return ResponseEntity.ok(response);
        } catch (PessoaNotFoundException ex) {
            throw ex;
        }
    }

    @Operation(
        summary = "Listar meus empréstimos ativos",
        description = "Retorna uma lista de empréstimos ativos para o usuário autenticado."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de empréstimos ativos retornada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/pessoa/me/ativos")
    public ResponseEntity<List<EmprestimoResponseDTO>> listarMeusEmprestimosAtivos(@AuthenticationPrincipal PessoaModel principal) {
        try {
            List<EmprestimoResponseDTO> response = emprestimoService.listarEmprestimosAtivosPorPessoa(principal.getIdPessoa());
            return ResponseEntity.ok(response);
        } catch (PessoaNotFoundException ex) {
            throw ex;
        }
    }

    @Operation(
        summary = "Listar empréstimos devolvidos por pessoa (admin)",
        description = "Retorna uma lista de empréstimos devolvidos para uma pessoa específica. Acesso restrito a administradores."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de empréstimos devolvidos retornada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pessoa/{idPessoa}/listardevolvidos")
    public ResponseEntity<List<EmprestimoResponseDTO>> ListarEmprestimosDevolvidos(
            @Parameter(description = "ID da pessoa", example = "1") @PathVariable Long idPessoa) {
        try {
            List<EmprestimoResponseDTO> response = emprestimoService.listarEmprestimosDevolvidos(idPessoa, "Devolvido");
            return ResponseEntity.ok(response);
        } catch (PessoaNotFoundException ex) {
            throw ex;
        }
    }

    @Operation(
        summary = "Listar meus empréstimos devolvidos",
        description = "Retorna uma lista de empréstimos devolvidos pelo usuário autenticado."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de empréstimos devolvidos retornada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/pessoa/me/devolvidos")
    public ResponseEntity<List<EmprestimoResponseDTO>> listarMeusEmprestimosDevolvidos(@AuthenticationPrincipal PessoaModel principal) {
        try {
            List<EmprestimoResponseDTO> response = emprestimoService.listarEmprestimosDevolvidos(principal.getIdPessoa(),"Devolvido");
            return ResponseEntity.ok(response);
        } catch (PessoaNotFoundException ex) {
            throw ex;
        }
    }
} 
