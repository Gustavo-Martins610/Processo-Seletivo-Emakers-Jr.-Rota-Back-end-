package com.emakers.api_biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emakers.api_biblioteca.models.EmprestimoId;
import com.emakers.api_biblioteca.models.EmprestimoModel;

public interface EmprestimoRepository extends JpaRepository<EmprestimoModel,EmprestimoId> {

}
