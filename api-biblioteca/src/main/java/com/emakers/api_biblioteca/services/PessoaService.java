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
import org.springframework.security.crypto.password.PasswordEncoder;

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

    public List<PessoaResponseDTO> pegartodaspessoas(){
        List<PessoaModel> pessoas = pessoaRepository.findAll();

        return pessoas.stream().map(PessoaResponseDTO::new).collect(Collectors.toList());
    }

    public PessoaResponseDTO pegarpessoaporid(Long idpessoa){
        PessoaModel pessoa = (pessoaRepository.findById(idpessoa).orElseThrow(()-> new IllegalArgumentException("ID não encontrado")));
        return new PessoaResponseDTO(pessoa);
    }

    public PessoaResponseDTO salvarpessoa(PessoaRequestDTO pessoaRequestDTO){
        PessoaModel pessoa = new PessoaModel(pessoaRequestDTO);
        pessoaRepository.save(pessoa);
        
        return new PessoaResponseDTO(pessoa);
    }

    public PessoaResponseDTO atualizarpessoa(Long idpessoa, PessoaUpdateDTO pessoaUpdateDTO){

        PessoaModel pessoa = (pessoaRepository.findById(idpessoa).orElseThrow(()-> new IllegalArgumentException("ID não encontrado")));
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

    public String deletarpessoa(Long idpessoa){
        PessoaModel pessoa = (pessoaRepository.findById(idpessoa).orElseThrow(()-> new RuntimeErrorException(null, "ID não encontrado")));
        pessoaRepository.delete(pessoa);

        return "pessoa de ID" + idpessoa + "deletada";
    }
}
