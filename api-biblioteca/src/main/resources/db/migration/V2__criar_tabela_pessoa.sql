CREATE TABLE IF NOT EXISTS Pessoa(
    id_pessoa BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(20) NOT NULL,
    cep VARCHAR(9),
    email VARCHAR(100),
    senha VARCHAR(100),
    role VARCHAR(10) NOT NULL,
    numero VARCHAR(8),
    logradouro VARCHAR(100),
    bairro VARCHAR(50),
    cidade VARCHAR(50),
    estado VARCHAR(2)
);