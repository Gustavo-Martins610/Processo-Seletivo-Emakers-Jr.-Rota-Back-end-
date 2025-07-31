-- Inserir dados na tabela pessoa
INSERT INTO pessoa (nome, cpf, cep, email, senha, numero, complemento, logradouro, bairro, cidade, estado, role) VALUES 
('João da Silva', '123.456.789-00', '35501-248', 'joao@email.com', 'senha123', '100', 'Apto 203', 'Rua Candeias', 'Centro', 'Lavras', 'MG', 'ADMIN'),
('Maria Oliveira', '987.654.321-00', '37500100', 'maria@email.com', 'senha456', '101', 'Apto 204', 'Rua do Sol', 'Centro', 'Lavras', 'MG', 'ADMIN'),
('Carlos Lima', '111.222.333-44', '37500200', 'carlos@email.com', 'senha789', '102', 'Casa 5', 'Rua das Flores', 'Centro', 'Lavras', 'USER'),
('Ana Beatriz', '222.333.444-55', '37500300', 'ana@email.com', 'senhaabc', '103', 'Apto 205', 'Rua Martins', 'São José', 'Lavras', 'USER'),
('Pedro Henrique', '333.444.555-66', '37500400', 'pedro@email.com', 'senhaxyz', '104', 'Casa 6', 'Avenida Brasil', 'Centro', 'Lavras', 'USER');

-- Inserir dados na tabela livro
INSERT INTO livro (nome, autor, data_lancamento, quantidade) VALUES 
('Dom Casmurro', 'Machado de Assis', '1899-01-01', 5),
('1984', 'George Orwell', '1949-06-08', 3),
('O Pequeno Príncipe', 'Antoine de Saint-Exupéry', '1943-04-06', 7),
('Memórias Póstumas de Brás Cubas', 'Machado de Assis', '1881-03-15', 4),
('A Revolução dos Bichos', 'George Orwell', '1945-08-17', 6);
