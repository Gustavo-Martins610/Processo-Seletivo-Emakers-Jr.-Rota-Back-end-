package com.emakers.api_biblioteca.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.emakers.api_biblioteca.repositories.PessoaRepository;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public AuthorizationService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
         return pessoaRepository.findPessoaByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + email));
}
}