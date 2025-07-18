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

import com.emakers.api_biblioteca.DTOs.PessoaRequestDTO;
import com.emakers.api_biblioteca.DTOs.PessoaResponseDTO;
import com.emakers.api_biblioteca.repositories.PessoaRepository;
import com.emakers.api_biblioteca.services.PessoaService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {


    @Autowired
    PessoaRepository PessoaRepository;
    @Autowired
    private PessoaService PessoaService ;

    @GetMapping(value = "/all") //o que é um endpoint e o que são esses mapping
    public ResponseEntity<List<PessoaResponseDTO>> pegartodaspessoas(){
        return ResponseEntity.status(HttpStatus.OK).body(PessoaService.pegartodaspessoas());
    }

    @GetMapping(value = "/{idPessoa}") //pq precisa dessas chaves
    public ResponseEntity<PessoaResponseDTO> pegarpessoaporid(@PathVariable Long idpessoa){
         return ResponseEntity.status(HttpStatus.OK).body(PessoaService.pegarpessoaporid(idpessoa));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<PessoaResponseDTO> salvarpessoa(@RequestBody PessoaRequestDTO pessoaRequestDTO){
        PessoaResponseDTO pessoaResponse = PessoaService.salvarpessoa(pessoaRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaResponse);
    }

    @PostMapping(value = "/update")
    public ResponseEntity <PessoaResponseDTO> mudarnomepesssoa(@Valid @PathVariable Long idpessoa, @RequestBody PessoaRequestDTO pessoaRequestDTO){
        return ResponseEntity.status(HttpStatus.OK).body(PessoaService.mudarnomepessoa(idpessoa,pessoaRequestDTO));
    }

    @DeleteMapping(value = "/delete/{idPessoa}")
    public ResponseEntity <String> deletarpessoa(Long idpessoa){
        return ResponseEntity.status(HttpStatus.OK).body(PessoaService.deletarpessoa(idpessoa));
    }
}
