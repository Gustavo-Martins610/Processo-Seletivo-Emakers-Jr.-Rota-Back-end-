package com.emakers.api_biblioteca.DTOs;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViaCepResponseDTO {
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
}


