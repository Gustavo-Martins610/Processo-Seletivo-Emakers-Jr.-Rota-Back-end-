package com.emakers.api_biblioteca.Testes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.emakers.api_biblioteca.models.PessoaModel;
import com.emakers.api_biblioteca.repositories.PessoaRepository;
import com.emakers.api_biblioteca.services.AuthorizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class AuthorizationServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private AuthorizationService authorizationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_UserFound() {
        
        String email = "email@teste.com";
        PessoaModel user = new PessoaModel();
        user.setEmail(email);

        when(pessoaRepository.findByEmail(email)).thenReturn(user);

        
        UserDetails result = authorizationService.loadUserByUsername(email);

        
        assertNotNull(result);
        assertEquals(email, result.getUsername());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        String email = "naoexiste@teste.com";
        when(pessoaRepository.findByEmail(email)).thenReturn(null);

        
        assertThrows(UsernameNotFoundException.class, () -> {
            authorizationService.loadUserByUsername(email);
        });
    }
}

