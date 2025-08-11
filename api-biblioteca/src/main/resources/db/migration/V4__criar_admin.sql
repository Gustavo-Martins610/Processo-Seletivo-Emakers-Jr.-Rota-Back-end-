INSERT INTO pessoa (nome, cpf, cep, email, senha, role)
VALUES ('Administrador','123.456.789-10','12345-278','gustavo981233@gmail.com', '$2b$12$0Yf7T9ZdN4cmgjLZcidD.eGgkti3QNjGdIN2fRHiTy4OoiDO5B636', 'ADMIN')
ON CONFLICT (email) DO NOTHING;