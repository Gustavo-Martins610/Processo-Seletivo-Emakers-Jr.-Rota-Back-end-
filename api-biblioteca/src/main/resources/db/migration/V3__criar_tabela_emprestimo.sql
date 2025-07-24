CREATE TABLE IF NOT EXISTS Emprestimo(
    id_emprestimo BIGSERIAL PRIMARY KEY,
    id_pessoa BIGINT NOT NULL,
    id_livro BIGINT NOT NULL,
    data_emprestimo DATE NOT NULL,
    data_devolucao DATE,


    CONSTRAINT fk_emprestimo_pessoa FOREIGN KEY (id_pessoa)
        REFERENCES Pessoa(id_pessoa) ON DELETE CASCADE,

    CONSTRAINT fk_emprestimo_livro FOREIGN KEY (id_livro)
        REFERENCES Livro(id_livro) ON DELETE CASCADE
);