package com.emakers.api_biblioteca.repositories;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.emakers.api_biblioteca.models.PessoaModel;


@Repository
public interface PessoaRepository extends JpaRepository <PessoaModel,Long> {
    
    PessoaModel findByEmail(String email);
    UserDetails findByIdPessoa(Long idPessoa);
    PessoaModel findPessoaByEmail(String email);
}
