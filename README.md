# Portfolio API

REST API developed with Spring Boot for managing portfolio projects.

## Technologies

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Docker
- Swagger/OpenAPI
- Maven
- Render

---

## Features

- CRUD projects
- Image upload
- Pagination
- Search by title
- Swagger documentation
- Dockerized application
- Cloud deploy on Render

---

## Architecture

src/main/java
├── controller
├── service
├── repository
├── dto
├── entity
├── config
└── exception

---

## API Documentation

Swagger UI:

https://portfolio-api-5kec.onrender.com/swagger-ui/index.html

---

## Base URL

https://portfolio-api-5kec.onrender.com

---

## Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | /projects | List projects |
| GET | /projects/{id} | Find by id |
| POST | /projects | Create project |
| PUT | /projects/{id} | Update project |
| DELETE | /projects/{id} | Delete project |

---

## Running locally

### Clone repository

```bash
git clone https://github.com/ronneyrv/portfolioapi.git

```

### Run PostgreSQL

```bash
env

DB_URL=
DB_USERNAME=
DB_PASSWORD=
```

### Run application

```bash
Bash

./mvnw spring-boot:run
```
---

## Docker

### Build image

```bash
Bash

docker build -t portfolio-api .
```
### Run container

```bash
Bash

docker run -p 8080:8080 portfolio-api
```
---

## Author

Ronney da Rocha Vieira

