package com.emakers.api_biblioteca.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emakers.api_biblioteca.DTOs.LivroRequestDTO;
import com.emakers.api_biblioteca.DTOs.LivroResponseDTO;
import com.emakers.api_biblioteca.models.LivroModel;
import com.emakers.api_biblioteca.repositories.LivroRepository;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public List<LivroResponseDTO> pegartodoslivros(){
        List<LivroModel> livros = livroRepository.findAll();

        return livros.stream().map(LivroResponseDTO::new).collect(Collectors.toList());
    }

    public LivroResponseDTO pegarlivroporid(Long idlivro){
        LivroModel livro = (livroRepository.findById(idlivro).orElseThrow(()-> new RuntimeErrorException(null, "ID não encontrado")));
        return new LivroResponseDTO(livro);
    }

    public LivroResponseDTO salvarlivro(LivroRequestDTO livroRequestDTO){
        LivroModel livro = new LivroModel(livroRequestDTO);
        livroRepository.save(livro);
        
        return new LivroResponseDTO(livro);
    }

    public LivroResponseDTO atualizarlivro(Long idlivro, LivroRequestDTO livroRequestDTO){
        LivroModel livro = (livroRepository.findById(idlivro).orElseThrow(()-> new RuntimeErrorException(null, "ID não encontrado")));
        if (livroRequestDTO.nome() != null && !livroRequestDTO.nome().isBlank()) {
            livro.setNome(livroRequestDTO.nome());
        }
        if (livroRequestDTO.autor() != null && !livroRequestDTO.autor().isBlank()) {
            livro.setAutor(livroRequestDTO.autor());
        }
        if (livroRequestDTO.data_lancamento() != null) {
            livro.setData_lancamento(livroRequestDTO.data_lancamento());
        }
        if (livroRequestDTO.quantidade() != null) {
            livro.setQuantidade(livroRequestDTO.quantidade());
        }
        
        livroRepository.save(livro);

        return new LivroResponseDTO(livro);
    }

    public String deletarlivro(Long idlivro){
        LivroModel livro = (livroRepository.findById(idlivro).orElseThrow(()-> new IllegalArgumentException("ID não encontrado")));
        livroRepository.delete(livro);

        return "Livro de ID " + idlivro + " deletado";
    }












}


