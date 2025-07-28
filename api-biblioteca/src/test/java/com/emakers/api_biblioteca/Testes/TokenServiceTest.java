package com.emakers.api_biblioteca.Testes;

import com.emakers.api_biblioteca.models.PessoaModel;
import com.emakers.api_biblioteca.services.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class TokenServiceTest {

    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        tokenService = new TokenService();
        // Definindo o secret manualmente, pois @Value não funciona no teste puro
        ReflectionTestUtils.setField(tokenService, "secret", "meu-segredo-teste");
    }

    @Test
    void testGenerateAndValidateToken_Success() {
        // Arrange
        PessoaModel pessoa = new PessoaModel();
        pessoa.setEmail("usuario@teste.com");

        // Act
        String token = tokenService.generateToken(pessoa);
        assertNotNull(token);

        // Validate
        String email = tokenService.validateToken(token);

        // Assert
        assertEquals("usuario@teste.com", email);
    }

    @Test
    void testValidateToken_InvalidToken() {
        // Act
        String result = tokenService.validateToken("token-invalido");

        // Assert
        assertEquals("", result);
    }
}
