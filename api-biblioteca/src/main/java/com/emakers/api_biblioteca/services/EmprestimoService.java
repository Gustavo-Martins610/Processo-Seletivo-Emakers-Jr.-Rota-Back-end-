package com.emakers.api_biblioteca.services;

import java.util.List;

import com.emakers.api_biblioteca.DTOs.EmprestimoRequestDTO;
import com.emakers.api_biblioteca.DTOs.EmprestimoResponseDTO;

public interface EmprestimoService {
    EmprestimoResponseDTO emprestarLivro(EmprestimoRequestDTO emprestimoRequestDTO);
    EmprestimoResponseDTO devolverLivro(Long idEmprestimo);
    List<EmprestimoResponseDTO> listarEmprestimosAtivosPorPessoa(Long idPessoa);
}
