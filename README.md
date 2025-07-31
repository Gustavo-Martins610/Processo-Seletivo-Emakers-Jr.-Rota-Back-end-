API Biblioteca - Documentação

1. Descrição do Projeto
Este projeto é uma API para gerenciamento de uma biblioteca, que permite a manipulação de dados de pessoas, livros e empréstimos. 
A aplicação utiliza a arquitetura RESTful para realizar operações como o cadastro de usuários, criação e devolução de empréstimos,
além da consulta a livros disponíveis na biblioteca. A API também é integrada a um serviço de CEP (ViaCEP) para validar e obter dados
relacionados ao endereço das pessoas.

Funcionalidades:
 - Cadastro de Pessoas: Permite a criação de novos usuários na biblioteca, com validações de e-mail e CPF.

 - Cadastro de Livros: Permite a inclusão de novos livros na biblioteca, com informações sobre nome, autor, quantidade, etc.

 - Empréstimos: A API possibilita realizar empréstimos de livros aos usuários, registrar a devolução e consultar empréstimos ativos.

 - Autenticação: Implementa um sistema de login e registro para os usuários, utilizando tokens JWT para autenticação.

 2. Como Rodar

2.1 Clonando o Repositório
Clone o repositório para o seu computador: git clone https://github.com/Gustavo-Martins610/Processo-Seletivo-Emakers-Jr.-Rota-Back-end-

2.2 Instalando Dependências
Certifique-se de que você tenha o Docker instalado. A seguir, você pode subir a aplicação com Docker para facilitar a configuração do banco de dados.
Subindo a API e o banco de dados com Docker
Se você já tiver configurado o arquivo docker-compose.yml, pode rodar o seguinte comando: docker-compose up --build
Esse comando irá criar e rodar os containers para a aplicação e o banco de dados PostgreSQL.

Caso não esteja utilizando o Docker, crie o banco de dados com nome basededados-biblioteca no PostgreSQL.

A partir do diretório raiz do projeto, execute o comando: ./mvnw spring-boot:run
Ou, caso esteja utilizando o Maven: mvn spring-boot:run

3. Como Testar
3.1 Testes de Unidade
Os testes de unidade estão localizados no diretório src/test/java do projeto. Você pode rodar os testes utilizando o Maven: mvn test

Isso irá rodar todos os testes presentes no projeto e exibir os resultados no terminal.

3.2 Testes Manuais
Para testar a API manualmente, você pode usar ferramentas como o Insomnia. Aqui estão alguns endpoints principais que você pode testar:

POST /auth/register: Registra um novo usuário.
Requer body JSON como por exemplo:
{
	"nome": "gustavo",
	"cpf": "092.105.926-46",
	"cep": "35501-248",
	"email": "gustavo981233@gmail.com",
	"senha": "123456789",
	"role": "ADMIN",
	"numero:": "980",
	"complemento": "AP202"
}

POST /auth/login: Realiza o login do usuário e retorna um token JWT.
Deve ser feito com algum usuário já registrado e exige body como por exemplo:
{
	"email": "gustavo981233@gmail.com",
	"senha": "123456789"
}
Além disso, o token que é retornado por esse endpoint é necessário para testar outros endpoints junto com o prefix "Bearer".

POST /livro/create: Cria um novo livro.
Função exclusiva para usuários com role "ADMIN" e requer token de login e body como por exemplo:
{
	"nome": "revolucao dos bichos",
	"autor": "Machado de assis",
	"data_lancamento": "1900-07-07",
	"quantidade": "10"
} 

POST /emprestimo/criar: Cria um empréstimo de livro.
Função disponível para qualquer usuário autenticado e requer token de login e body como por exemplo:
{
	"idPessoa": "1",
	"idLivro": "1"
}

POST /emprestimo/devolver/{idEmprestimo}: Registra a devolução de um livro.
Função disponível para qualquer usuário autenticado e requer token de login e o id do emprestimo a ser devolvido deve ser colocado à frente da URL.

Esses endpoints podem ser testados para verificar a criação e autenticação de usuários, empréstimos e livros.
Além disso, para acessar a documentação da api feita pelo Swagger é só abrir o navegador com a aplicação rodando e digitar:
http://localhost:8080/swagger-ui/index.html
Na documentação é possível ver todos os endpoints da API.

4. Tecnologias Utilizadas
Java 17: Linguagem principal utilizada no desenvolvimento da API.
Spring Boot 3: Framework para criação da aplicação.
PostgreSQL: Banco de dados relacional utilizado para armazenamento de dados.
Docker: Usado para criação de containers para a API e banco de dados.
Flyway: Ferramenta para migrações de banco de dados.
Spring Security: Para implementação de autenticação via JWT.
JUnit 5: Framework utilizado para testes unitários.
Mockito: Biblioteca utilizada para mocks nos testes.
Swagger/OpenAPI: Para documentação da API.
Lombok: Para reduzir a verbosidade do código com anotações como @Getter, @Setter, @Builder, etc.

Autor: Gustavo Martins de oliveira [gustavo981233@gmail.com ou gustavo.oliveira20@estudante.ufla.br]
