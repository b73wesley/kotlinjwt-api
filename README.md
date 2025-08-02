# JWT API

API desenvolvida em Kotlin utilizando Spring Boot para gerenciamento de autenticação JWT.

> **Aviso:** Este projeto foi desenvolvido apenas para fins de estudo e testes. O foco não foi seguir padrões arquiteturais ou melhores práticas de produção.

## Tecnologias

- Kotlin
- Spring Boot
- Gradle
- JWT (io.jsonwebtoken)
- SQL

## Como executar

1. Clone o repositório.
2. Gere uma chave JWT segura (mínimo 32 caracteres/base64).
3. Copiar o nome da chave no arquivo `application.properties`
4. Em 'Configurations' do IntelliJ, adicione a variável de ambiente `JWT_KEY` com um valor de chave valida.
5. Ainda em configurations selecione 'KotlinJwtApiApplicationKt' como classe principal e 'D:\Dev\Kotlin Projects\kotlinjwt-api' como Working Directory e para o modulo 'KotlinJwt-api'.
6. Execute a aplicação.