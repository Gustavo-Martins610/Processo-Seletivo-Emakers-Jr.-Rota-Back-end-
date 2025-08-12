package com.emakers.api_biblioteca.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emakers.api_biblioteca.DTOs.LivroRequestDTO;
import com.emakers.api_biblioteca.DTOs.LivroResponseDTO;
import com.emakers.api_biblioteca.models.LivroModel;
import com.emakers.api_biblioteca.repositories.LivroRepository;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Serviço responsável pelas operações de CRUD relacionadas à entidade Livro.
 */
@Tag(name = "Serviço de Livros", description = "Contém as regras de negócio para criação, atualização, listagem e remoção de livros.")
@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    /**
     * Retorna todos os livros cadastrados na base de dados.
     *
     * @return Lista de DTOs de resposta com os dados dos livros.
     */
    public List<LivroResponseDTO> pegartodoslivros() {
        List<LivroModel> livros = livroRepository.findAll();
        return livros.stream().map(LivroResponseDTO::new).collect(Collectors.toList());
    }

    /**
     * Retorna os dados de um livro específico pelo ID.
     *
     * @param idlivro ID do livro a ser buscado.
     * @return DTO contendo os dados do livro encontrado.
     * @throws IllegalArgumentException se o ID não existir no banco de dados.
     */
    public LivroResponseDTO pegarlivroporid(Long idlivro) {
        LivroModel livro = livroRepository.findById(idlivro)
            .orElseThrow(() -> new IllegalArgumentException("ID não encontrado"));
        return new LivroResponseDTO(livro);
    }

    /**
     * Cria e salva um novo livro na base de dados.
     *
     * @param livroRequestDTO DTO contendo os dados do livro a ser criado.
     * @return DTO com os dados do livro salvo.
     */
    public LivroResponseDTO salvarlivro(LivroRequestDTO livroRequestDTO) {
        LivroModel livro = new LivroModel(livroRequestDTO);
        livroRepository.save(livro);
        return new LivroResponseDTO(livro);
    }

    /**
     * Atualiza os dados de um livro existente com base no ID.
     * Apenas os campos não nulos e não em branco serão atualizados.
     *
     * @param idlivro ID do livro a ser atualizado.
     * @param livroRequestDTO DTO com os dados a serem modificados.
     * @return DTO com os dados atualizados do livro.
     * @throws IllegalArgumentException se o livro com o ID especificado não for encontrado.
     */
    public LivroResponseDTO atualizarlivro(Long idlivro, LivroRequestDTO livroRequestDTO) {
        LivroModel livro = livroRepository.findById(idlivro)
            .orElseThrow(() -> new IllegalArgumentException("ID não encontrado"));

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

    /**
     * Remove um livro da base de dados com base no ID.
     *
     * @param idlivro ID do livro a ser removido.
     * @return Mensagem de confirmação indicando o ID removido.
     * @throws IllegalArgumentException se o livro com o ID especificado não for encontrado.
     */
    public String deletarlivro(Long idlivro) {
        LivroModel livro = livroRepository.findById(idlivro)
            .orElseThrow(() -> new IllegalArgumentException("ID não encontrado"));
        livroRepository.delete(livro);
        return "Livro de ID " + idlivro + " deletado";
    }
}
