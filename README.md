# Portfolio API

REST API developed with Spring Boot for managing portfolio projects.

## Technologies

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Swagger/OpenAPI
- Lombok
- Maven

## Architecture

src/main/java
├── controller
├── service
├── repository
├── dto
├── entity
├── exception
└── config

## Features

- CRUD de projetos
- Upload de imagens
- Swagger documentation
- Exception handling
- DTO architecture

## Running locally

```bash
git clone https://github.com/ronneyrv/portfolio-api

cd portfolio-api

./mvnw spring-boot:run
```

## Database

PostgreSQL

Create database:

CREATE DATABASE portfolio_db;

## Swagger

http://localhost:8080/swagger-ui/index.html

## Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | /projects | List projects |
| GET | /projects/{id} | Find project |
| POST | /projects | Create project |
| PUT | /projects/{id} | Update project |
| DELETE | /projects/{id} | Delete project |

## Author

Ronney da Rocha Vieira

