CREATE TABLE IF NOT EXISTS Livro(
    id_livro BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    data_lancamento DATE,
    quantidade INTEGER NOT NULL DEFAULT 1
);