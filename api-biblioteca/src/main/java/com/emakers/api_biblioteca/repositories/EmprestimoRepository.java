package com.emakers.api_biblioteca.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emakers.api_biblioteca.models.EmprestimoModel;

@Repository
public interface EmprestimoRepository extends JpaRepository<EmprestimoModel,Long> {
    long countByPessoaIdPessoaAndDataDevolucaoIsNull(Long idPessoa);
    List<EmprestimoModel> findByPessoaIdPessoaAndDataDevolucaoIsNull(Long idPessoa);
    List<EmprestimoModel> findByPessoa_IdPessoaAndStatus(Long idpessoa,String status);
}
