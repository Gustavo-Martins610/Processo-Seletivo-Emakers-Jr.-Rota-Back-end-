package com.emakers.api_biblioteca.models;

import jakarta.persistence.Embeddable;
import java.util.Objects;


@Embeddable
public class EmprestimoId{

    private Long idPessoa;
    private Long idLivro;

    public Long getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Long idPessoa) {
        this.idPessoa = idPessoa;
    }

    public Long getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(Long idLivro) {
        this.idLivro = idLivro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmprestimoId)) return false;
        EmprestimoId that = (EmprestimoId) o;
        return Objects.equals(idPessoa, that.idPessoa) &&
               Objects.equals(idLivro, that.idLivro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPessoa, idLivro);
    }
}
