package com.emakers.api_biblioteca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.emakers.api_biblioteca.DTOs.EmprestimoRequestDTO;
import com.emakers.api_biblioteca.DTOs.EmprestimoResponseDTO;
import com.emakers.api_biblioteca.services.EmprestimoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/emprestimo")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;

    @PostMapping(value = "/criar")
    public ResponseEntity<EmprestimoResponseDTO> emprestarLivro(@Valid @RequestBody EmprestimoRequestDTO emprestimoRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(emprestimoService.emprestarLivro(emprestimoRequestDTO));
    }

    @PostMapping(value = "/devolver/{idPessoa}/{idLivro}")
    public ResponseEntity<EmprestimoResponseDTO> devolverLivro(@PathVariable Long idPessoa, @PathVariable Long idLivro) {
        return ResponseEntity.status(HttpStatus.OK).body(emprestimoService.devolverLivro(idPessoa, idLivro));
    }
}