package com.emakers.api_biblioteca.models;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class EmprestimoId implements Serializable {

    private UUID idPessoa;
    private UUID idLivro;

    public UUID getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(UUID idPessoa) {
        this.idPessoa = idPessoa;
    }

    public UUID getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(UUID idLivro) {
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
