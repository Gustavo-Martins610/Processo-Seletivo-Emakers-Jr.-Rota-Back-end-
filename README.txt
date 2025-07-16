Repositório criado para processo seletivo da Emakers

✅ Checklist de Desenvolvimento – API Biblioteca

    Etapa 0: Preparação do Ambiente
- [x] Instalar Java JDK 17+
- [x] Instalar Git e configurar conta GitHub
- [x] Instalar IDE (VS Code, IntelliJ ou outra)
- [x] Configurar PostgreSQL/MySQL ou usar H2
- [x] Testar criação de projeto Spring Boot (via Spring Initializr)

---

    Etapa 1: Organização e Projeto Inicial
- [x] Criar repositório público no GitHub
- [x] Clonar repositório localmente
- [x] Criar projeto com Spring Initializr
- [x] Subir projeto base para o GitHub (commit inicial)

---

    Etapa 2: Modelagem e Banco de Dados
- [x] Analisar modelo relacional fornecido
- [x] Criar entidade `Pessoa`
- [x] Criar entidade `Livro`
- [x] Criar entidade de **Empréstimo** (relação M:N)
- [x] Configurar `application.properties` com dados do banco
- [x] Criar estrutura básica do banco via JPA

---

    Etapa 3: CRUDs Base
Para cada entidade (`Pessoa`, `Livro`):
- [ ] Criar classe `Entity`
- [ ] Criar `Repository`
- [ ] Criar `Service`
- [ ] Criar `Controller`
- [ ] Validar com `@Valid` / `@NotNull`
- [ ] Testar os endpoints com o Postman

---

    Etapa 4: Funcionalidades Especiais
- [ ] Criar endpoint para **empréstimo de livro**
- [ ] Criar endpoint para **devolução de livro**
- [ ] Atualizar quantidade de exemplares do livro ao emprestar/devolver
- [ ] Integrar com API externa ViaCEP para preencher endereço da `Pessoa`

---

    Etapa 5: Segurança e Documentação
- [ ] Implementar autenticação com **Spring Security**
- [ ] Proteger rotas com autenticação
- [ ] Documentar a API com **Swagger**
- [ ] Criar tratamento global de exceções (`@ControllerAdvice`)

---

    Etapa 6: Funcionalidades Extras (Recomendadas)
- [ ] Subir aplicação com **Docker**
- [ ] Implementar **limite de empréstimos por pessoa**
- [ ] Listar livros atualmente emprestados por pessoa
- [ ] Atualizar campo `quantidade` de livros automaticamente
- [ ] Adicionar testes automatizados (Junit/Mockito)
- [ ] Aplicar boas práticas REST (status HTTP, verbos corretos)
- [ ] Usar **padrão de commits semântico**:
  - `add:` nova funcionalidade
  - `fix:` correção de bug
  - `update:` refatoração

---

    Etapa 7: Finalização e Entrega
- [ ] Garantir que todos os requisitos estão implementados
- [ ] Atualizar `README.md` com:
  - [ ] Descrição do projeto
  - [ ] Como rodar
  - [ ] Como testar
  - [ ] Tecnologias utilizadas
- [ ] Subir versão final no GitHub
- [ ] Enviar link do repositório no Discord da Emakers
