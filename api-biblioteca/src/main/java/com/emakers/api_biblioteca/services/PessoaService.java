package com.emakers.api_biblioteca.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emakers.api_biblioteca.DTOs.PessoaRequestDTO;
import com.emakers.api_biblioteca.DTOs.PessoaResponseDTO;
import com.emakers.api_biblioteca.DTOs.PessoaUpdateDTO;
import com.emakers.api_biblioteca.DTOs.ViaCepResponseDTO;
import com.emakers.api_biblioteca.models.PessoaModel;
import com.emakers.api_biblioteca.repositories.PessoaRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Serviço responsável pelas regras de negócio relacionadas à entidade Pessoa.
 */
@Tag(name = "Serviço de Pessoas", description = "Gerencia operações de criação, atualização, busca e remoção de pessoas.")
@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ViaCepService viaCepService;

    public void setViaCepService(ViaCepService viaCepService) {
        this.viaCepService = viaCepService;
    }

    /**
     * Retorna todas as pessoas cadastradas.
     *
     * @return Lista de DTOs com os dados das pessoas.
     */
    public List<PessoaResponseDTO> pegartodaspessoas() {
        List<PessoaModel> pessoas = pessoaRepository.findAll();
        return pessoas.stream().map(PessoaResponseDTO::new).collect(Collectors.toList());
    }

    /**
     * Retorna uma pessoa específica pelo ID.
     *
     * @param idpessoa ID da pessoa.
     * @return DTO com os dados da pessoa encontrada.
     * @throws IllegalArgumentException se o ID não existir.
     */
    public PessoaResponseDTO pegarpessoaporid(Long idpessoa) {
        PessoaModel pessoa = pessoaRepository.findById(idpessoa)
            .orElseThrow(() -> new IllegalArgumentException("ID não encontrado"));
        return new PessoaResponseDTO(pessoa);
    }

    /**
     * Cadastra uma nova pessoa no sistema.
     *
     * @param pessoaRequestDTO DTO com os dados da nova pessoa.
     * @return DTO com os dados salvos.
     */
    public PessoaResponseDTO salvarpessoa(PessoaRequestDTO pessoaRequestDTO) {
        PessoaModel pessoa = new PessoaModel(pessoaRequestDTO);
        pessoaRepository.save(pessoa);
        return new PessoaResponseDTO(pessoa);
    }

    /**
     * Atualiza parcialmente os dados de uma pessoa existente.
     * Campos nulos ou em branco são ignorados.
     *
     * @param idpessoa ID da pessoa a ser atualizada.
     * @param pessoaUpdateDTO DTO com os dados de atualização.
     * @return DTO com os dados atualizados.
     * @throws IllegalArgumentException se o ID não for encontrado.
     */
    public PessoaResponseDTO atualizarpessoa(Long idpessoa, PessoaUpdateDTO pessoaUpdateDTO) {
        PessoaModel pessoa = pessoaRepository.findById(idpessoa)
            .orElseThrow(() -> new IllegalArgumentException("ID não encontrado"));

        if (pessoaUpdateDTO.nome() != null && !pessoaUpdateDTO.nome().isBlank()) {
            pessoa.setNome(pessoaUpdateDTO.nome());
        }

        if (pessoaUpdateDTO.cep() != null && !pessoaUpdateDTO.cep().isBlank()) {
            ViaCepResponseDTO endereco = viaCepService.consultarCep(pessoaUpdateDTO.cep());
            pessoa.setLogradouro(endereco.getLogradouro());
            pessoa.setBairro(endereco.getBairro());
            pessoa.setCidade(endereco.getLocalidade());
            pessoa.setEstado(endereco.getUf());
            pessoa.setCep(pessoaUpdateDTO.cep());
        }

        if (pessoaUpdateDTO.email() != null && !pessoaUpdateDTO.email().isBlank()) {
            pessoa.setEmail(pessoaUpdateDTO.email());
        }

        if (pessoaUpdateDTO.senha() != null && !pessoaUpdateDTO.senha().isBlank()) {
            pessoa.setSenha(passwordEncoder.encode(pessoaUpdateDTO.senha()));
        }

        if (pessoaUpdateDTO.numero() != null && !pessoaUpdateDTO.numero().isBlank()) {
            pessoa.setNumero(pessoaUpdateDTO.numero());
        }

        if (pessoaUpdateDTO.complemento() != null && !pessoaUpdateDTO.complemento().isBlank()) {
            pessoa.setComplemento(pessoaUpdateDTO.complemento());
        }

        pessoaRepository.save(pessoa);
        return new PessoaResponseDTO(pessoa);
    }

    /**
     * Remove uma pessoa da base de dados.
     *
     * @param idpessoa ID da pessoa a ser deletada.
     * @return Mensagem de confirmação.
     * @throws RuntimeErrorException se o ID não for encontrado.
     */
    public String deletarpessoa(Long idpessoa) {
        PessoaModel pessoa = pessoaRepository.findById(idpessoa)
            .orElseThrow(() -> new RuntimeErrorException(null, "ID não encontrado"));
        pessoaRepository.delete(pessoa);
        return "Pessoa de ID " + idpessoa + " deletada";
    }

    /**
     * Busca uma pessoa pelo ID (versão redundante da pegarpessoaporid).
     *
     * @param idPessoa ID da pessoa.
     * @return DTO da pessoa.
     * @throws EntityNotFoundException se a pessoa não for encontrada.
     */
    public PessoaResponseDTO buscarPorId(Long idPessoa) {
        PessoaModel pessoa = pessoaRepository.findById(idPessoa)
            .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada"));
        return new PessoaResponseDTO(pessoa);
    }

}
