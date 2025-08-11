package com.emakers.api_biblioteca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emakers.api_biblioteca.DTOs.PessoaResponseDTO;
import com.emakers.api_biblioteca.Users.UserRole;
import com.emakers.api_biblioteca.exceptions.CredenciaisInvalidasException;
import com.emakers.api_biblioteca.models.PessoaModel;
import com.emakers.api_biblioteca.repositories.PessoaRepository;


@RestController
@RequestMapping("/admin/users")
public class AdminController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @PatchMapping("/tornar-admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PessoaResponseDTO> tornarAdmin(@PathVariable Long id) {
        PessoaModel pessoa = pessoaRepository.findById(id).orElseThrow(() -> new CredenciaisInvalidasException("ID não encontrado"));
        pessoa.setRole(UserRole.ADMIN);
        pessoaRepository.save(pessoa);
        PessoaResponseDTO resposta = new PessoaResponseDTO(pessoa);
        return ResponseEntity.ok(resposta);
    }
}