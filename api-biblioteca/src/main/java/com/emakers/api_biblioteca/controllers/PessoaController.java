package com.emakers.api_biblioteca.controllers;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


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
@Tag(name = "Pessoa", description = "Operações relacionadas à entidade Pessoa")
public class PessoaController {

    
    @Autowired
    ViaCepService viaCepService;

    @Autowired
    PessoaRepository pessoaRepository;
    @Autowired
    private PessoaService pessoaService;

    @Operation(summary = "Listar todas as pessoas")
    @ApiResponse(responseCode = "200", description = "Lista de pessoas")
    @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    @GetMapping(value = "/all")
    public ResponseEntity<List<PessoaResponseDTO>> pegartodaspessoas(){
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.pegartodaspessoas());
    }

    @Operation(summary = "Buscar pessoa por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pessoa encontrada"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada"),
        @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @GetMapping(value = "/{idPessoa}")
    public ResponseEntity<PessoaResponseDTO> pegarpessoaporid(@PathVariable("idPessoa") @Parameter(description = "ID da pessoa", example = "1") Long idpessoa){
         return ResponseEntity.status(HttpStatus.OK).body(pessoaService.pegarpessoaporid(idpessoa));
    }

     @Operation(summary = "Criar uma nova pessoa")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pessoa criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida"),
        @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @PostMapping(value = "/create")
    public ResponseEntity<PessoaResponseDTO> salvarpessoa(@RequestBody @Parameter(description = "Dados para criar uma nova pessoa") PessoaRequestDTO pessoaRequestDTO){
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


    @Operation(summary = "Atualizar uma pessoa")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pessoa atualizada"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada"),
        @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @PostMapping(value = "/update/{idPessoa}")
    public ResponseEntity <PessoaResponseDTO> mudarnomepessoa(@Valid @PathVariable("idPessoa") @Parameter(description = "ID da pessoa", example = "1") Long idpessoa,
     @RequestBody @Parameter(description = "Dados atualizados da pessoa") PessoaRequestDTO pessoaRequestDTO){
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.mudarnomepessoa(idpessoa,pessoaRequestDTO));
    }


    @Operation(summary = "Deletar uma pessoa")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Pessoa deletada"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada"),
        @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @DeleteMapping(value = "/delete/{idPessoa}")
    public ResponseEntity <String> deletarpessoa(@PathVariable("idPessoa") @Parameter(description = "ID da pessoa", example = "1") Long idpessoa){
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.deletarpessoa(idpessoa));
    }
}
