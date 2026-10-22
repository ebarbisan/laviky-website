# Lavanderia Laviky - Website Institucional

Este é o website institucional da **Lavanderia Laviky**, reconstruído integralmente em Java com Spring Boot para garantir máxima performance, segurança e escalabilidade, substituindo a implementação anterior em PHP.

## 🚀 Tecnologias Utilizadas

- **Backend:** Java 21 LTS, Spring Boot 3.3.4
- **Persistência:** MySQL 8, Spring Data JPA, Hibernate, Flyway (Migrations)
- **Frontend:** Thymeleaf, Bootstrap 5, HTML5, CSS3, JavaScript ES6+
- **Integrações:** Google Places API (Avaliações), Spring Mail (SMTP)
- **Build & Gestão:** Maven

## 📁 Estrutura do Projeto

```text
laviky/
├── src/main/java/br/com/laviky/
│   ├── config/      # Configurações globais da aplicação
│   ├── controller/  # Controladores de rotas (Web e API)
│   ├── service/     # Camada de regras de negócio
│   ├── repository/  # Interfaces de acesso ao banco de dados
│   ├── model/       # Entidades JPA / Modelos de domínio
│   ├── dto/         # Objetos de transferência de dados
│   ├── validation/  # Validadores customizados (ex: CNPJ)
│   ├── security/    # Configurações de Spring Security e CSRF
│   └── util/        # Classes utilitárias
├── src/main/resources/
│   ├── db/migration/ # Scripts SQL de migração do Flyway
│   ├── templates/    # Páginas HTML renderizadas via Thymeleaf
│   └── static/       # Arquivos estáticos (CSS, JS, Imagens)
└── uploads/          # Diretório para armazenamento seguro de anexos
```

## 🛠️ Configuração e Execução Local

### Pré-requisitos
- Java 21 LTS
- Maven 3.9+
- MySQL 8.0+

### 1. Banco de Dados
Crie o banco de dados no MySQL:
```sql
CREATE DATABASE laviky_db;
```

### 2. Variáveis de Ambiente
Crie um arquivo `.env` ou configure as seguintes variáveis no seu ambiente:

| Variável | Descrição | Exemplo |
| :--- | :--- | :--- |
| `SPRING_DATASOURCE_URL` | URL de conexão com MySQL | `jdbc:mysql://localhost:3306/laviky_db` |
| `SPRING_DATASOURCE_USERNAME` | Usuário do banco | `root` |
| `SPRING_DATASOURCE_PASSWORD` | Senha do banco | `senha123` |
| `SPRING_MAIL_HOST` | Host SMTP | `smtp.gmail.com` |
| `SPRING_MAIL_PORT` | Porta SMTP | `587` |
| `SPRING_MAIL_USERNAME` | Usuário SMTP | `contato@laviky.com.br` |
| `SPRING_MAIL_PASSWORD` | Senha SMTP | `app-password` |
| `LAVIKY_MAIL_FROM` | E-mail de remetente | `noreply@laviky.com.br` |
| `LAVIKY_CONTACT_EMAIL` | E-mail para receber leads | `comercial@laviky.com.br` |
| `GOOGLE_PLACES_API_KEY` | Chave da API do Google | `AIzaSy...` |
| `GOOGLE_PLACE_ID` | ID do local no Google Maps | `ChIJ...` |
| `LAVIKY_UPLOAD_DIR` | Pasta de uploads | `uploads/` |

### 3. Executando a Aplicação
Utilizando o Maven Wrapper:
```bash
./mvnw spring-boot:run
```
A aplicação estará disponível em: `http://localhost:8080`

## 🛡️ Segurança Implementada

- **Proteção Contra SQL Injection:** Uso de Spring Data JPA com queries parametrizadas.
- **Proteção CSRF:** Habilitada via Spring Security com `CookieCsrfTokenRepository`.
- **Uploads Seguros:** 
  - Validação rigorosa de extensões (PDF, JPG, PNG).
  - Verificação de MIME-Type e tamanho máximo (5MB).
  - Renomeação de arquivos com UUID para evitar *Path Traversal*.
  - Armazenamento fora do diretório público.
- **Validação de Dados:** Bean Validation (Jakarta) no backend + validação matemática de CNPJ.
- **XSS Prevention:** Thymeleaf realiza o escape automático de conteúdo.

## 📈 Testes Automatizados

Para executar a suíte de testes:
```bash
./mvnw test
```
As coberturas incluem:
- Validação de CNPJs válidos e inválidos.
- Processamento de solicitações empresariais.
- Restrições de tipos de arquivos em uploads.
- Integridade da persistência no MySQL.

## 📝 Observações Adicionais
- **Google Reviews:** A integração consome a API de Places. Se as chaves não forem configuradas, o sistema exibe uma mensagem amigável informando a indisponibilidade das avaliações sem quebrar a página.
- **LGPD:** O sistema registra IP e User-Agent apenas para fins de auditoria de solicitações comerciais, seguindo princípios de minimização.
