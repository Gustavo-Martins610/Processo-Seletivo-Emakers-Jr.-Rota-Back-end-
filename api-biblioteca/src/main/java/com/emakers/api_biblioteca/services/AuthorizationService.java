package com.emakers.api_biblioteca.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.emakers.api_biblioteca.repositories.PessoaRepository;

import io.swagger.v3.oas.annotations.Hidden;

/**
 * Serviço de autenticação utilizado pelo Spring Security para carregar os dados do usuário
 * com base no e-mail informado. Este serviço é utilizado internamente pelo mecanismo de
 * autenticação e não é exposto como endpoint REST.
 */
@Hidden // Oculta este serviço da documentação Swagger
@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private PessoaRepository pessoaRepository;

    /**
     * Construtor explícito usado para injeção de dependência.
     *
     * @param pessoaRepository repositório de pessoas
     */
    public AuthorizationService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    /**
     * Carrega os detalhes do usuário pelo e-mail.
     *
     * @param email o e-mail do usuário
     * @return os detalhes do usuário implementando UserDetails
     * @throws UsernameNotFoundException se nenhum usuário for encontrado com o e-mail informado
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return pessoaRepository.findPessoaByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + email));
    }
}
