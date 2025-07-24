package com.emakers.api_biblioteca.controllers;

import java.util.List;

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

@RestController
@RequestMapping("/livro")
public class LivroController {

@Autowired
LivroRepository LivroRepository;
    @Autowired
    private LivroService livroService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<LivroResponseDTO>> pegartodoslivros(){
        return ResponseEntity.status(HttpStatus.OK).body(livroService.pegartodoslivros());
    }

    @GetMapping(value = "/{idLivro}")
    public ResponseEntity<LivroResponseDTO> pegarlivroporid(@PathVariable("idLivro") Long idlivro){
         return ResponseEntity.status(HttpStatus.OK).body(livroService.pegarlivroporid(idlivro));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<LivroResponseDTO> salvarlivro(@Valid @RequestBody LivroRequestDTO livroRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.salvarlivro(livroRequestDTO));
    }

    @PostMapping(value = "/update")
    public ResponseEntity <LivroResponseDTO> mudarnomelivro(@Valid @PathVariable Long idlivro,@Valid @RequestBody LivroRequestDTO livroRequestDTO){
        return ResponseEntity.status(HttpStatus.OK).body(livroService.mudarnomelivro(idlivro,livroRequestDTO));
    }

    @DeleteMapping(value = "/delete/{idLivro}")
    public ResponseEntity <String> deletarlivro(@PathVariable("idLivro") Long idlivro){
        return ResponseEntity.status(HttpStatus.OK).body(livroService.deletarlivro(idlivro));
    }

}

