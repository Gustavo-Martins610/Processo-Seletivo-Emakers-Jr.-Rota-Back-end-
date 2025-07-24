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
import com.emakers.api_biblioteca.DTOs.ViaCepResponseDTO;
import com.emakers.api_biblioteca.models.PessoaModel;
import com.emakers.api_biblioteca.repositories.PessoaRepository;
import com.emakers.api_biblioteca.services.PessoaService;
import com.emakers.api_biblioteca.services.ViaCepService;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {
    @Autowired
    ViaCepService viaCepService;

    @Autowired
    PessoaRepository pessoaRepository;
    @Autowired
    private PessoaService pessoaService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<PessoaResponseDTO>> pegartodaspessoas(){
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.pegartodaspessoas());
    }

    @GetMapping(value = "/{idPessoa}")
    public ResponseEntity<PessoaResponseDTO> pegarpessoaporid(@PathVariable("idPessoa") Long idpessoa){
         return ResponseEntity.status(HttpStatus.OK).body(pessoaService.pegarpessoaporid(idpessoa));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<PessoaResponseDTO> salvarpessoa(@RequestBody PessoaRequestDTO pessoaRequestDTO){
        ViaCepResponseDTO endereco = viaCepService.consultarCep(pessoaRequestDTO.cep());
        PessoaModel pessoa = new PessoaModel();
        pessoa.setNome(pessoaRequestDTO.nome());
        pessoa.setCpf(pessoaRequestDTO.cpf());
        pessoa.setCep(pessoaRequestDTO.cep());
        pessoa.setEmail(pessoaRequestDTO.email());
        pessoa.setSenha(pessoaRequestDTO.senha());
        pessoa.setRole(pessoaRequestDTO.role());
        pessoa.setNumero(pessoaRequestDTO.numero());
        pessoa.setComplemento(pessoaRequestDTO.complemento());
        pessoa.setLogradouro(endereco.getLogradouro());
        pessoa.setBairro(endereco.getBairro());
        pessoa.setCidade(endereco.getLocalidade());
        pessoa.setEstado(endereco.getUf());

        pessoaRepository.save(pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(new PessoaResponseDTO(pessoa));
    }

    @PostMapping(value = "/update/{idPessoa}")
    public ResponseEntity <PessoaResponseDTO> mudarnomepessoa(@Valid @PathVariable("idPessoa") Long idpessoa, @RequestBody PessoaRequestDTO pessoaRequestDTO){
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.mudarnomepessoa(idpessoa,pessoaRequestDTO));
    }

    @DeleteMapping(value = "/delete/{idPessoa}")
    public ResponseEntity <String> deletarpessoa(@PathVariable("idPessoa") Long idpessoa){
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.deletarpessoa(idpessoa));
    }
}
