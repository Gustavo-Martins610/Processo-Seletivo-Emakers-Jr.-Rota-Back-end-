Repositório criado para processo seletivo da Emakers

✅ Checklist de Desenvolvimento – API Biblioteca

    Etapa 0: Preparação do Ambiente
- [] Instalar Java JDK 17+
- [] Instalar Git e configurar conta GitHub
- [] Instalar IDE (VS Code, IntelliJ ou outra)
- [] Configurar PostgreSQL/MySQL ou usar H2
- [] Testar criação de projeto Spring Boot (via Spring Initializr)

---

    Etapa 1: Organização e Projeto Inicial
- [] Criar repositório público no GitHub
- [] Clonar repositório localmente
- [] Criar projeto com Spring Initializr
- [] Subir projeto base para o GitHub (commit inicial)

---

    Etapa 2: Modelagem e Banco de Dados
- [] Analisar modelo relacional fornecido
- [] Criar entidade `Pessoa`
- [] Criar entidade `Livro`
- [] Criar entidade de **Empréstimo** (relação M:N)
- [] Configurar `application.properties` com dados do banco
- [] Criar estrutura básica do banco via JPA

---

    Etapa 3: CRUDs Base
Para cada entidade (`Pessoa`, `Livro`):
- [] Criar classe `Entity`
- [] Criar `Repository`
- [] Criar `Service`
- [] Criar `Controller`
- [] Validar com `@Valid` / `@NotNull`
- [] Testar os endpoints com o Insominia

---

    Etapa 4: Funcionalidades Especiais
- [] Criar endpoint para **empréstimo de livro**
- [] Criar endpoint para **devolução de livro**
- [] Atualizar quantidade de exemplares do livro ao emprestar/devolver
- [ ] Integrar com API externa ViaCEP para preencher endereço da `Pessoa`

---

    Etapa 5: Segurança e Documentação
- [ ] Implementar autenticação com **Spring Security**
- [ ] Proteger rotas com autenticação
- [ ] Documentar a API com **Swagger**
- [x] Criar tratamento global de exceções (`@ControllerAdvice`)

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
