package com.emakers.api_biblioteca.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.emakers.api_biblioteca.DTOs.ViaCepResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Serviço responsável por consultar dados de endereço a partir de um CEP usando a API pública ViaCEP.
 */
@Tag(name = "Serviço ViaCEP", description = "Consulta endereços através do CEP usando a API pública do ViaCEP.")
@Service
public class ViaCepService {

    private RestTemplate restTemplate = new RestTemplate();

    /**
     * Construtor que permite injeção personalizada de `RestTemplate`.
     * 
     * @param restTemplate instância de RestTemplate a ser usada
     */
    public ViaCepService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Consulta o serviço ViaCEP com o CEP informado e retorna os dados de endereço correspondentes.
     *
     * @param cep CEP a ser consultado
     * @return DTO com informações de logradouro, bairro, cidade, estado, etc.
     */
    @Operation(summary = "Consulta CEP", description = "Consulta o endereço correspondente ao CEP informado usando a API ViaCEP.")
    public ViaCepResponseDTO consultarCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        return restTemplate.getForObject(url, ViaCepResponseDTO.class);
    }
}
