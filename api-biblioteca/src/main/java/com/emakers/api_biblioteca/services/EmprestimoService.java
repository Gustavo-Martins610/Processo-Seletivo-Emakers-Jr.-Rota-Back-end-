package com.emakers.api_biblioteca.services;

import com.emakers.api_biblioteca.DTOs.EmprestimoRequestDTO;
import com.emakers.api_biblioteca.DTOs.EmprestimoResponseDTO;

public interface EmprestimoService {
    EmprestimoResponseDTO emprestarLivro(EmprestimoRequestDTO emprestimoRequestDTO);
    EmprestimoResponseDTO devolverLivro(Long idPessoa, Long idLivro);
}
