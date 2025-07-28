package com.emakers.api_biblioteca.Testes;

import com.emakers.api_biblioteca.DTOs.ViaCepResponseDTO;
import com.emakers.api_biblioteca.services.ViaCepService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ViaCepServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ViaCepService viaCepService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConsultarCep_Success() {
        String cep = "37200000";
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        ViaCepResponseDTO fakeResponse = new ViaCepResponseDTO();
        fakeResponse.setCep(cep);
        fakeResponse.setLocalidade("Lavras");
        fakeResponse.setUf("MG");

        when(restTemplate.getForObject(url, ViaCepResponseDTO.class)).thenReturn(fakeResponse);

        ViaCepResponseDTO result = viaCepService.consultarCep(cep);

        assertNotNull(result);
        assertEquals(cep, result.getCep());
        assertEquals("Lavras", result.getLocalidade());
        assertEquals("MG", result.getUf());
    }
}
