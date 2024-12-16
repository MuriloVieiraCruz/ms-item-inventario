## Gerenciamento de Item de Inventário - Microsserviço
Este projeto tem como objetivo o desenvolvimento de um microsserviço para gerenciar itens de inventário.

### Escopo
O sistema permitirá o gerenciamento completo de itens de inventário, incluindo:

* Cadastro de um item.
* Alteração de um item.
* Listagem de um item.
* Exclusão de um item.

### Arquitetura
O projeto segue uma arquitetura de microsserviços, onde um único serviço é responsável por gerenciar todas as funcionalidades do sistema de gerenciamento de item de inventário.

### Tecnologias Utilizadas
* Java 11 com Spring Boot versão 2.3.2: Base do microsserviço.
* Spring Data JPA: Interação com o banco de dados.
* Spring Data JPA: Interação com o banco de dados.
* Banco de Dados: PostgreSQ versão 10.

#### Instalação e Configuração

Pré-requisitos:
* Java 11
* Spring Boot versão 2.3.2
* Maven
* PostgreSQL versão 10

### Passos para Executar o Projeto
Clone o repositório:
```
git clone https://github.com/MuriloVieiraCruz/ms-item-inventario.git
```

### Configure o banco de dados no arquivo application.yml:

```
spring:
  jpa:
    hibernate:
      ddl-auto: update
  datasource:
    url: jdbc:postgresql://localhost:5433/seu_banco
    username: root
    password: root
    driver-class-name: org.postgresql.Driver
```

### Configuração do banco via Docker:

Caso tenha o docker instalado na máquina, apenas execute o arquivo docker-compose, ele pode ser encontrado em:

```
docker/docker-compose.yml
```

Após isso no diretório da pasta execute o comendo para subir o banco de dados:

```
docker compose up -d
```

### Execute o projeto:

```
mvn spring-boot:run
```

### Collection do Postman para realizar as requisições:
[teste_farol_collection.zip](https://github.com/user-attachments/files/18143981/teste_farol_collection.zip)

### Front-end para integração com o back-end:
[front-ms-item-inventario](https://github.com/MuriloVieiraCruz/front-ms-item-inventario)
