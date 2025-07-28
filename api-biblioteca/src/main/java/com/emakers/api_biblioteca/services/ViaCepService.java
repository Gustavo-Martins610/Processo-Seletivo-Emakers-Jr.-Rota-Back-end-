package com.emakers.api_biblioteca.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.emakers.api_biblioteca.DTOs.ViaCepResponseDTO;
@Service
public class ViaCepService {

    private RestTemplate restTemplate = new RestTemplate();
    public ViaCepService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ViaCepResponseDTO consultarCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        return restTemplate.getForObject(url, ViaCepResponseDTO.class);
    }
}
