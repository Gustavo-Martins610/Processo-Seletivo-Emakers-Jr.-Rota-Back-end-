package com.emakers.api_biblioteca.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emakers.api_biblioteca.Enums.LivroCategoria;
import com.emakers.api_biblioteca.models.LivroModel;

@Repository
public interface LivroRepository extends JpaRepository<LivroModel,Long> {
    List<LivroModel> findAllByCategoria(LivroCategoria categoria);
}
