CREATE TABLE IF NOT EXISTS Pessoa(
    id_pessoa BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(20) NOT NULL UNIQUE,
    cep VARCHAR(9),
    email VARCHAR(100) UNIQUE,
    senha VARCHAR(100),
    numero VARCHAR(10),
    complemento VARCHAR(10),
    logradouro VARCHAR(100),
    bairro VARCHAR(50),
    cidade VARCHAR(50),
    estado VARCHAR(2),
    role VARCHAR(5)
);