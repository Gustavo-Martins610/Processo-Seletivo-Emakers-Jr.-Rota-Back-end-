package com.emakers.api_biblioteca.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emakers.api_biblioteca.DTOs.PessoaRequestDTO;
import com.emakers.api_biblioteca.DTOs.PessoaResponseDTO;
import com.emakers.api_biblioteca.DTOs.ViaCepResponseDTO;
import com.emakers.api_biblioteca.Users.LoginResponseDTO;
import com.emakers.api_biblioteca.models.PessoaModel;
import com.emakers.api_biblioteca.repositories.PessoaRepository;
import com.emakers.api_biblioteca.services.TokenService;
import com.emakers.api_biblioteca.services.ViaCepService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("auth")

public class AuthenticationController {

    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    ViaCepService viaCepService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid PessoaRequestDTO pessoaRequestDTO){
        var pessoaSenha = new UsernamePasswordAuthenticationToken(pessoaRequestDTO.email(), pessoaRequestDTO.senha());
        var auth = this.authenticationManager.authenticate(pessoaSenha);

        var token = tokenService.generateToken((PessoaModel)auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<PessoaResponseDTO> register(@RequestBody @Valid PessoaRequestDTO pessoaRequestDTO){
        if(this.pessoaRepository.findByEmail(pessoaRequestDTO.email()) != null){
        return ResponseEntity.badRequest().build();
        }

        String encryptedSenha = new BCryptPasswordEncoder().encode(pessoaRequestDTO.senha());
        ViaCepResponseDTO endereco = viaCepService.consultarCep(pessoaRequestDTO.cep());
        PessoaModel pessoa = new PessoaModel();
        pessoa.setNome(pessoaRequestDTO.nome());
        pessoa.setCpf(pessoaRequestDTO.cpf());
        pessoa.setCep(pessoaRequestDTO.cep());
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
