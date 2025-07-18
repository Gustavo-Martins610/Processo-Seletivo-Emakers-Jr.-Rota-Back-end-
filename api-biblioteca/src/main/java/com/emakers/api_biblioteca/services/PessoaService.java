package com.emakers.api_biblioteca.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emakers.api_biblioteca.DTOs.PessoaRequestDTO;
import com.emakers.api_biblioteca.DTOs.PessoaResponseDTO;
import com.emakers.api_biblioteca.models.PessoaModel;
import com.emakers.api_biblioteca.repositories.PessoaRepository;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<PessoaResponseDTO> pegartodaspessoas(){
        List<PessoaModel> pessoas = pessoaRepository.findAll();

        return pessoas.stream().map(PessoaResponseDTO::new).collect(Collectors.toList());
    }

    public PessoaResponseDTO pegarpessoaporid(Long idpessoa){
        PessoaModel pessoa = (pessoaRepository.findById(idpessoa).orElseThrow(()-> new RuntimeErrorException(null, "ID não encontrado")));
        return new PessoaResponseDTO(pessoa);
    }

    public PessoaResponseDTO salvarpessoa(PessoaRequestDTO pessoaRequestDTO){
        PessoaModel pessoa = new PessoaModel(pessoaRequestDTO);
        pessoaRepository.save(pessoa);
        
        return new PessoaResponseDTO(pessoa);
    }

    public PessoaResponseDTO mudarnomepessoa(Long idpessoa, PessoaRequestDTO pessoaRequestDTO){
        PessoaModel pessoa = (pessoaRepository.findById(idpessoa).orElseThrow(()-> new RuntimeErrorException(null, "ID não encontrado")));
        pessoa.setNome(pessoaRequestDTO.nome());
        pessoaRepository.save(pessoa);

        return new PessoaResponseDTO(pessoa);
    }

    public String deletarpessoa(Long idpessoa){
        PessoaModel pessoa = (pessoaRepository.findById(idpessoa).orElseThrow(()-> new RuntimeErrorException(null, "ID não encontrado")));
        pessoaRepository.delete(pessoa);

        return "pessoa de ID" + idpessoa + "deletada";
    }
}
