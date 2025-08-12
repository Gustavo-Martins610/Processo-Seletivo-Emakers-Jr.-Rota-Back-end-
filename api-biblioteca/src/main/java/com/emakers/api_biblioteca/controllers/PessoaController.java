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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.emakers.api_biblioteca.DTOs.PessoaRequestDTO;
import com.emakers.api_biblioteca.DTOs.PessoaResponseDTO;
import com.emakers.api_biblioteca.DTOs.PessoaUpdateDTO;
import com.emakers.api_biblioteca.DTOs.ViaCepResponseDTO;
import com.emakers.api_biblioteca.Users.UserRole;
import com.emakers.api_biblioteca.exceptions.PessoaNotFoundException;
import com.emakers.api_biblioteca.exceptions.ValidationException;
import com.emakers.api_biblioteca.models.PessoaModel;
import com.emakers.api_biblioteca.repositories.PessoaRepository;
import com.emakers.api_biblioteca.services.PessoaService;
import com.emakers.api_biblioteca.services.ViaCepService;

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

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void setPessoaService(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }
    public void setViaCepService(ViaCepService viaCepService) {
        this.viaCepService = viaCepService;
    }
    public void setPessoaRepository(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar todas as pessoas")
    @ApiResponse(responseCode = "200", description = "Lista de pessoas")
    @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    @GetMapping("/all")
    public ResponseEntity<List<PessoaResponseDTO>> pegartodaspessoas() {
        return ResponseEntity.ok(pessoaService.pegartodaspessoas());
    }

    @Operation(summary = "Buscar pessoa por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pessoa encontrada"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada"),
        @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{idPessoa}")
    public ResponseEntity<PessoaResponseDTO> pegarpessoaporid(
            @PathVariable("idPessoa") @Parameter(description = "ID da pessoa", example = "1") Long idpessoa) {
        try {
            PessoaResponseDTO response = pessoaService.pegarpessoaporid(idpessoa);
            return ResponseEntity.ok(response);
        } catch (PessoaNotFoundException ex) {
            throw ex;
        }
    }

    @Operation(summary = "Buscar os próprios dados do usuário logado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Dados do usuário logado"),
        @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    @GetMapping("/me/meusdados")
    public ResponseEntity<PessoaResponseDTO> eu(@AuthenticationPrincipal PessoaModel principal) {
        if (principal == null) {
            return ResponseEntity.status(401).build();
        }
        var dto = pessoaService.buscarPorId(principal.getIdPessoa());
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Criar uma nova pessoa")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pessoa criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida"),
        @ApiResponse(responseCode = "409", description = "Email já cadastrado"),
        @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<PessoaResponseDTO> salvarpessoa(
            @Valid @RequestBody @Parameter(description = "Dados para criar uma nova pessoa") PessoaRequestDTO pessoaRequestDTO) {
        try {
            if (pessoaRepository.findByEmail(pessoaRequestDTO.email()) != null) {
                throw new ValidationException("Email já cadastrado.");
            }
            ViaCepResponseDTO endereco = viaCepService.consultarCep(pessoaRequestDTO.cep());
            PessoaModel pessoa = new PessoaModel();
            pessoa.setNome(pessoaRequestDTO.nome());
            pessoa.setCpf(pessoaRequestDTO.cpf());
            pessoa.setCep(pessoaRequestDTO.cep());
            pessoa.setEmail(pessoaRequestDTO.email());
            pessoa.setSenha(passwordEncoder.encode(pessoaRequestDTO.senha()));
            pessoa.setNumero(pessoaRequestDTO.numero());
            pessoa.setComplemento(pessoaRequestDTO.complemento());
            pessoa.setLogradouro(endereco.getLogradouro());
            pessoa.setBairro(endereco.getBairro());
            pessoa.setCidade(endereco.getLocalidade());
            pessoa.setEstado(endereco.getUf());
            pessoa.setRole(UserRole.USER);

            pessoaRepository.save(pessoa);
            return ResponseEntity.status(HttpStatus.CREATED).body(new PessoaResponseDTO(pessoa));
        } catch (ValidationException ex) {
            throw ex;
        }
    }

    @Operation(summary = "Atualizar uma pessoa pelo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pessoa atualizada"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida"),
        @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update/{idPessoa}")
    public ResponseEntity<PessoaResponseDTO> atualizarpessoa(
            @Valid @PathVariable("idPessoa") @Parameter(description = "ID da pessoa", example = "1") Long idpessoa,
            @Valid @RequestBody @Parameter(description = "Dados atualizados da pessoa") PessoaUpdateDTO pessoaupdateDTO) {
        try {
            PessoaResponseDTO response = pessoaService.atualizarpessoa(idpessoa, pessoaupdateDTO);
            return ResponseEntity.ok(response);
        } catch (PessoaNotFoundException | ValidationException ex) {
            throw ex;
        }
    }

    @Operation(summary = "Atualizar os próprios dados")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @PatchMapping("/update/me")
    public ResponseEntity<PessoaResponseDTO> atualizarMeusDados(@AuthenticationPrincipal PessoaModel principal, @Valid @RequestBody PessoaUpdateDTO pessoaupdateDTO) {
        try {
            PessoaResponseDTO response = pessoaService.atualizarpessoa(principal.getIdPessoa(), pessoaupdateDTO);
            return ResponseEntity.ok(response);
        } catch (PessoaNotFoundException | ValidationException ex) {
            throw ex;
        }
    }

    @Operation(summary = "Deletar uma pessoa")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Pessoa deletada"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada"),
        @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{idPessoa}")
    public ResponseEntity<Void> deletarpessoa(
            @PathVariable("idPessoa") @Parameter(description = "ID da pessoa", example = "1") Long idpessoa) {
        try {
            pessoaService.deletarpessoa(idpessoa);
            return ResponseEntity.noContent().build();
        } catch (PessoaNotFoundException ex) {
            throw ex;
        }
    }
}
