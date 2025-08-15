package com.emakers.api_biblioteca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emakers.api_biblioteca.DTOs.PessoaResponseDTO;
import com.emakers.api_biblioteca.Enums.UserRole;
import com.emakers.api_biblioteca.exceptions.CredenciaisInvalidasException;
import com.emakers.api_biblioteca.models.PessoaModel;
import com.emakers.api_biblioteca.repositories.PessoaRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/admin/users")
public class AdminController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @PatchMapping("/tornar-admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Conceder privilégio de administrador a um usuário",
        description = "Permite que um administrador promova outro usuário para a role ADMIN, com base no ID fornecido."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário promovido com sucesso para ADMIN"),
        @ApiResponse(responseCode = "403", description = "Acesso negado. Somente administradores podem executar esta ação."),
        @ApiResponse(responseCode = "404", description = "Usuário com o ID especificado não foi encontrado")
    })

    
    public ResponseEntity<PessoaResponseDTO> tornarAdmin(@Parameter(description = "ID do usuário que será promovido", example = "5")@PathVariable Long id) {

        PessoaModel pessoa = pessoaRepository.findById(id).orElseThrow(() -> new CredenciaisInvalidasException("ID não encontrado"));

        pessoa.setRole(UserRole.ADMIN);
        pessoaRepository.save(pessoa);

        PessoaResponseDTO resposta = new PessoaResponseDTO(pessoa);
        return ResponseEntity.ok(resposta);
    }

}
