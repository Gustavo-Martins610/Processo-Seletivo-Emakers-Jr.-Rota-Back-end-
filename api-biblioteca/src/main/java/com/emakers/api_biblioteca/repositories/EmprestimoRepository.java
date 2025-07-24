package com.emakers.api_biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emakers.api_biblioteca.models.EmprestimoModel;

@Repository
public interface EmprestimoRepository extends JpaRepository<EmprestimoModel,Long> {
    long countByPessoaIdPessoaAndDataDevolucaoIsNull(Long idPessoa);
}
