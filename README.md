# FórumHub API

## Descrição

O **FórumHub API** é uma API REST desenvolvida em Java que simula o funcionamento de um fórum online. A aplicação permite o gerenciamento de tópicos, usuários, cursos e respostas com sistema completo de autenticação JWT e controle de acesso baseado em roles. Foi criada como parte do desafio do programa ONE (Oracle Next Education) da Alura, seguindo os princípios de Clean Architecture e SOLID.

## Funcionalidades Principais

### 1. Autenticação e Autorização
- **Registro de usuários**: Cadastro seguro com validação de email único e criptografia de senha
- **Autenticação JWT**: Sistema de login com geração de tokens JWT seguros
- **Controle de acesso baseado em roles**: Diferenciação entre usuários ADMIN e USER
- **Validação de propriedade**: Usuários podem editar apenas seus próprios tópicos

### 2. Gerenciamento de Tópicos
- **Criação de tópicos**: Cadastro de novos tópicos com validação completa
- **Listagem paginada**: Múltiplas estratégias de listagem (paginada, completa, limitada)
- **Busca avançada**: Filtros por nome do curso e ano específico
- **Atualização de tópicos**: Edição com controle de propriedade e autorização
- **Exclusão de tópicos**: Remoção restrita a usuários ADMIN

### 3. Sistema de Paginação Avançado
- **Paginação padrão**: 10 resultados por página, ordenados por data de criação
- **Listagem completa**: Endpoint para visualizar todos os tópicos
- **Limite personalizado**: Endpoint com parâmetro de limite flexível
- **Busca com filtros**: Combinação de curso e ano para resultados específicos

## Regras de Negócio

### Gestão de Usuários
- **Emails únicos**: Não permite cadastro de usuários com emails duplicados
- **Criptografia**: Senhas criptografadas com BCrypt antes do armazenamento
- **Roles automáticas**: Usuários recebem role USER por padrão no cadastro
- **Autorização hierárquica**: ADMIN pode gerenciar todos os recursos

### Gestão de Tópicos
- **Campos obrigatórios**: Todos os campos do tópico são mandatórios
- **Prevenção de duplicatas**: Não permite tópicos com mesmo título e mensagem
- **Status controlado**: Sistema de status ABERTO/SOLUCIONADO
- **Propriedade**: Apenas o autor ou ADMIN pode modificar um tópico
- **Exclusão restrita**: Somente ADMIN pode deletar tópicos

### Segurança e Validação
- **Autenticação obrigatória**: Todos os endpoints protegidos (exceto registro e login)
- **Tokens JWT**: Algoritmo HMAC256 com expiração configurável
- **Validação de entrada**: Validações abrangentes com Bean Validation
- **Tratamento de erros**: Respostas JSON consistentes para todos os erros

## Tecnologias Utilizadas

- **Java 17+**: Linguagem principal
- **Spring Boot 3.x**: Framework de desenvolvimento
- **Spring Security**: Autenticação e autorização
- **Spring Data JPA**: ORM para persistência
- **MySQL**: Banco de dados relacional
- **JWT (Auth0)**: Geração e validação de tokens
- **Flyway**: Controle de versão do banco de dados
- **Lombok**: Redução de boilerplate
- **Bean Validation**: Validação de dados
- **Maven**: Gerenciamento de dependências

## Arquitetura

### Clean Architecture
A aplicação segue os princípios de Clean Architecture com separação clara de responsabilidades:

- **API Layer**: Controllers, DTOs e configurações REST
- **Application Layer**: Use cases e serviços de aplicação
- **Domain Layer**: Entidades de negócio e interfaces (framework-agnostic)
- **Infrastructure Layer**: Implementações de persistência e segurança

### Estrutura de Diretórios
```
src/main/java/br/com/alura/forumhub_api/
├── api/              # Controllers, DTOs, exceções globais
├── application/      # Use cases e serviços de aplicação
├── domain/           # Entidades, enums e interfaces de repositório
└── infra/            # Persistência, segurança e configurações
```

### Padrões Implementados
- **Clean Architecture**: Separação de camadas com dependências unidirecionais
- **SOLID Principles**: Aplicação dos cinco princípios fundamentais
- **Repository Pattern**: Abstração do acesso aos dados
- **Use Case Pattern**: Encapsulamento da lógica de negócio
- **DTO Pattern**: Separação entre dados de transporte e entidades

## Endpoints da API

### Autenticação
- `POST /register` - Cadastro de usuário (retorna Location header)
- `POST /login` - Autenticação e geração de token JWT

### Tópicos
- `POST /topicos` - Criação de tópico (retorna Location header)
- `GET /topicos` - Listagem paginada (padrão: page=0, size=10, sort=createdAt ASC)
- `GET /topicos/all` - Listagem completa sem paginação
- `GET /topicos/limited?limit=X` - Listagem com limite personalizado
- `GET /topicos/search?curso=X&ano=Y` - Busca por curso e ano
- `GET /topicos/{id}` - Detalhes de um tópico específico
- `PUT /topicos/{id}` - Atualização de tópico (autor ou ADMIN)
- `DELETE /topicos/{id}` - Exclusão de tópico (apenas ADMIN)

## Banco de Dados

### Schema
- **users**: Informações dos usuários com senha criptografada
- **roles**: Definição de papéis (ADMIN, USER)
- **users_roles**: Relacionamento many-to-many entre usuários e roles
- **courses**: Catálogo de cursos disponíveis
- **topics**: Tópicos do fórum com referências para usuário e curso
- **replies**: Respostas aos tópicos (estrutura preparada)

### Migrações
Controle de versão do banco com Flyway:
- V1: Tabela de usuários
- V2: Tabela de cursos
- V3: Tabela de tópicos
- V4: Tabela de respostas
- V5: Tabela de roles
- V6: Tabela de relacionamento usuários-roles

## Como Executar

### Pré-requisitos
- Java 17 ou superior
- MySQL instalado e configurado
- Maven 3.6 ou superior

### Configuração
Configure as propriedades no `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/forumhub
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
api.security.token.secret=sua_chave_secreta_jwt
api.security.token.expiration=3600000
```

### Execução
```bash
mvn spring-boot:run
```

## Segurança

### Características de Segurança
- **Criptografia de senhas**: BCrypt com salt aleatório
- **Tokens JWT seguros**: HMAC256 com chave secreta configurável
- **Controle de acesso granular**: Validação por método e por recurso
- **Tratamento de erros seguro**: Respostas JSON sem vazamento de informações
- **Validação de entrada**: Sanitização e validação de todos os dados recebidos
- **Headers de segurança**: Configurações adequadas no Spring Security