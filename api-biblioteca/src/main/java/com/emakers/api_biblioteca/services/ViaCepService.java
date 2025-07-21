package com.emakers.api_biblioteca.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.emakers.api_biblioteca.DTOs.ViaCepResponseDTO;
@Service
public class ViaCepService {
    private final RestTemplate restTemplate = new RestTemplate();

    public ViaCepResponseDTO consultarCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        return restTemplate.getForObject(url, ViaCepResponseDTO.class);
    }
}
