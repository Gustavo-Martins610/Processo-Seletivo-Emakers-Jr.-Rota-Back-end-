package com.emakers.api_biblioteca.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emakers.api_biblioteca.DTOs.PessoaRequestDTO;
import com.emakers.api_biblioteca.DTOs.PessoaResponseDTO;
import com.emakers.api_biblioteca.DTOs.ViaCepResponseDTO;
import com.emakers.api_biblioteca.Users.LoginResponseDTO;
import com.emakers.api_biblioteca.exceptions.CredenciaisInvalidasException;
import com.emakers.api_biblioteca.exceptions.EmailJaCadastradoException;
import com.emakers.api_biblioteca.models.PessoaModel;
import com.emakers.api_biblioteca.repositories.PessoaRepository;
import com.emakers.api_biblioteca.services.TokenService;
import com.emakers.api_biblioteca.services.ViaCepService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("auth")
@Tag(name = "Autenticação", description = "Endpoints para login e registro de usuários")
public class AuthenticationController {

    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    ViaCepService viaCepService;

    @Operation(
        summary = "Login do usuário",
        description = "Autentica um usuário existente. Retorna o token JWT se as credenciais estiverem corretas."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
        @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid PessoaRequestDTO pessoaRequestDTO){
        try {
            var pessoaSenha = new UsernamePasswordAuthenticationToken(pessoaRequestDTO.email(), pessoaRequestDTO.senha());
            var auth = this.authenticationManager.authenticate(pessoaSenha);

            var token = tokenService.generateToken((PessoaModel)auth.getPrincipal());
            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (BadCredentialsException e) {
            throw new CredenciaisInvalidasException("E-mail ou senha inválidos.");
        }
    }

    @Operation(
        summary = "Registrar novo usuário",
        description = "Cria um novo usuário na aplicação. Retorna o token JWT e as informações do usuário."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso"),
        @ApiResponse(responseCode = "409", description = "E-mail já cadastrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/register")
    public ResponseEntity<PessoaResponseDTO> register(@RequestBody @Valid PessoaRequestDTO pessoaRequestDTO){
        if(this.pessoaRepository.findByEmail(pessoaRequestDTO.email()) != null){
            throw new EmailJaCadastradoException("E-mail já cadastrado.");
        }

        String encryptedSenha = new BCryptPasswordEncoder().encode(pessoaRequestDTO.senha());
        ViaCepResponseDTO endereco = viaCepService.consultarCep(pessoaRequestDTO.cep());
        PessoaModel pessoa = new PessoaModel();
        pessoa.setNome(pessoaRequestDTO.nome());
        pessoa.setCpf(pessoaRequestDTO.cpf());
        pessoa.setCep(pessoaRequestDTO.cep());
        pessoa.setComplemento(pessoaRequestDTO.complemento());
        pessoa.setEmail(pessoaRequestDTO.email());
        pessoa.setSenha(encryptedSenha);
        pessoa.setRole(pessoaRequestDTO.role());
        pessoa.setNumero(pessoaRequestDTO.numero());
        pessoa.setLogradouro(endereco.getLogradouro());
        pessoa.setBairro(endereco.getBairro());
        pessoa.setCidade(endereco.getLocalidade());
        pessoa.setEstado(endereco.getUf());

        this.pessoaRepository.save(pessoa);
        return ResponseEntity.ok().build();
    }

}
