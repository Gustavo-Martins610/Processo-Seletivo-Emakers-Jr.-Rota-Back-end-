package com.emakers.api_biblioteca.repositories;



import com.emakers.api_biblioteca.models.PessoaModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<PessoaModel,Long> {

    UserDetails findByEmail(String email);

}
