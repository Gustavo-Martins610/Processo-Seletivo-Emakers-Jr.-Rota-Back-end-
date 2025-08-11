package com.emakers.api_biblioteca.Testes;

import com.emakers.api_biblioteca.DTOs.PessoaRequestDTO;
import com.emakers.api_biblioteca.DTOs.PessoaResponseDTO;
import com.emakers.api_biblioteca.DTOs.ViaCepResponseDTO;
import com.emakers.api_biblioteca.Users.LoginResponseDTO;
import com.emakers.api_biblioteca.controllers.AuthenticationController;
import com.emakers.api_biblioteca.exceptions.CredenciaisInvalidasException;
import com.emakers.api_biblioteca.exceptions.EmailJaCadastradoException;
import com.emakers.api_biblioteca.models.PessoaModel;
import com.emakers.api_biblioteca.repositories.PessoaRepository;
import com.emakers.api_biblioteca.services.TokenService;
import com.emakers.api_biblioteca.services.ViaCepService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationControllerTest {

    private PessoaRepository pessoaRepository;
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private ViaCepService viaCepService;
    private AuthenticationController controller;

    @BeforeEach
    void setUp() {
        pessoaRepository = mock(PessoaRepository.class);
        authenticationManager = mock(AuthenticationManager.class);
        tokenService = mock(TokenService.class);
        viaCepService = mock(ViaCepService.class);
        controller = new AuthenticationController();

        // Injeta dependências (reflexão porque os campos são package-private)
        try {
            var repoField = AuthenticationController.class.getDeclaredField("pessoaRepository");
            repoField.setAccessible(true);
            repoField.set(controller, pessoaRepository);

            var authField = AuthenticationController.class.getDeclaredField("authenticationManager");
            authField.setAccessible(true);
            authField.set(controller, authenticationManager);

            var tokenField = AuthenticationController.class.getDeclaredField("tokenService");
            tokenField.setAccessible(true);
            tokenField.set(controller, tokenService);

            var viaCepField = AuthenticationController.class.getDeclaredField("viaCepService");
            viaCepField.setAccessible(true);
            viaCepField.set(controller, viaCepService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testLogin_Success() {
        PessoaRequestDTO loginReq = new PessoaRequestDTO(
            "fulano", "12345678901", "35500-200", "fulano@email.com", "senha123",
            "123", "AP202", "rua martins", "São José", "Lavras", "MG");

        // Mocks para autenticação e geração de token
        Authentication auth = mock(Authentication.class);
        PessoaModel pessoa = new PessoaModel();
        pessoa.setEmail("fulano@email.com");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        when(auth.getPrincipal()).thenReturn(pessoa);
        when(tokenService.generateToken(any(PessoaModel.class))).thenReturn("tokendeexemplo");

        ResponseEntity<LoginResponseDTO> response = controller.login(loginReq);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("tokendeexemplo", response.getBody().token());
    }

    @Test
    void testLogin_BadCredentials() {
        PessoaRequestDTO loginReq = new PessoaRequestDTO(
            "fulano", "12345678901", "35500-200", "fulano@email.com", "senhaerrada",
            "123", "AP202", "rua martins", "São José", "Lavras", "MG");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        assertThrows(CredenciaisInvalidasException.class, () -> {
            controller.login(loginReq);
        });
    }

    @Test
    void testRegister_Success() {
        PessoaRequestDTO req = new PessoaRequestDTO(
            "fulano", "12345678901", "35500-200", "fulano@email.com", "senha123",
            "123", "AP202", "rua martins", "São José", "Lavras", "MG");
        when(pessoaRepository.findByEmail("fulano@email.com")).thenReturn(null);
        when(viaCepService.consultarCep("35500-200")).thenReturn(
            new ViaCepResponseDTO("35500-200","rua martins","São José","Lavras","MG")
        );

        // Espera só um ResponseEntity.ok()
        ResponseEntity<PessoaResponseDTO> response = controller.register(req);

        assertEquals(200, response.getStatusCode().value());
        verify(pessoaRepository).save(any(PessoaModel.class));
    }

    @Test
    void testRegister_EmailJaCadastrado() {
        PessoaRequestDTO req = new PessoaRequestDTO(
            "fulano", "12345678901", "35500-200", "fulano@email.com", "senha123",
            "123", "AP202", "rua martins", "São José", "Lavras", "MG");
        when(pessoaRepository.findByEmail("fulano@email.com")).thenReturn(new PessoaModel());

        assertThrows(EmailJaCadastradoException.class, () -> {
            controller.register(req);
        });
    }
}
