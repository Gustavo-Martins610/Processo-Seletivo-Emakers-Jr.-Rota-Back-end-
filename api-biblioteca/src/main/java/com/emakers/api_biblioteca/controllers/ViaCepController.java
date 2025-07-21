package com.emakers.api_biblioteca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emakers.api_biblioteca.DTOs.PessoaRequestDTO;
import com.emakers.api_biblioteca.DTOs.PessoaResponseDTO;
import com.emakers.api_biblioteca.DTOs.ViaCepResponseDTO;
import com.emakers.api_biblioteca.models.PessoaModel;
import com.emakers.api_biblioteca.services.ViaCepService;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

import com.emakers.api_biblioteca.repositories.PessoaRepository;

@RestController
@RequestMapping("/pessoa-com-cep")
public class ViaCepController {
    @Autowired
    private ViaCepService viaCepService;
    
    @Autowired
    private PessoaRepository pessoaRepository;

    @PostMapping("/create")
    public ResponseEntity<PessoaResponseDTO>salvarPessoa(@Valid @RequestBody PessoaRequestDTO dto) {
    ViaCepResponseDTO endereco = viaCepService.consultarCep(dto.cep());

    PessoaModel pessoa = new PessoaModel();
    pessoa.setNome(dto.nome());
    pessoa.setCpf(dto.cpf());
    pessoa.setCep(dto.cep());
    pessoa.setEmail(dto.email());
    pessoa.setSenha(dto.senha());
    pessoa.setNumero(dto.numero());
    pessoa.setLogradouro(endereco.getLogradouro());
    pessoa.setBairro(endereco.getBairro());
    pessoa.setCidade(endereco.getLocalidade());
    pessoa.setEstado(endereco.getUf());

    pessoaRepository.save(pessoa);
    return ResponseEntity.status(HttpStatus.CREATED).body(new PessoaResponseDTO(pessoa));
    }
}
