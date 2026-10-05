# Portfolio API

REST API developed with Spring Boot for managing portfolio projects.

## Technologies

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Docker
- Spring Security
- JWT
- Swagger/OpenAPI
- Maven
- Cloudinary
- Oracle Cloud

---

## Features

- Project CRUD
- Administrative authentication
- JWT authentication
- Role-based access control
- Project image upload
- Project image management
- Pagination
- Search by title
- Swagger documentation
- Dockerized application
- Cloud deployment
- Cloudinary image storage
- CORS configuration for production and local environments

---

## Architecture

```text
src/main/java
├── controller
├── service
├── repository
├── dto
├── entity
├── config
├── security
└── exception
```

---

## Authentication

The API uses **JWT (JSON Web Token)** for authentication.

Administrative operations require an authenticated user with the `ADMIN` role.

### Login

```http
POST /auth/login
```

Example request:

```json
{
  "username": "admin",
  "password": "your-password"
}
```

The API returns a JWT token that must be sent in the `Authorization` header for protected endpoints:

```http
Authorization: Bearer <token>
```

---

## API Documentation

Swagger UI:

https://portfolio-api.ronneyrocha.com.br/swagger-ui/index.html

---

## Base URL

https://portfolio-api.ronneyrocha.com.br

---

## Endpoints

### Public endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/projects` | List projects |
| GET | `/projects/{slug}` | Find project by slug |
| GET | `/projects/search` | Search projects |
| POST | `/auth/login` | Authenticate administrator |
| GET | `/auth/me` | Get authenticated user |

### Administrative endpoints

The following operations require authentication with the `ADMIN` role:

| Method | Endpoint | Description |
|---|---|---|
| POST | `/projects` | Create project |
| PUT | `/projects/{id}` | Update project |
| DELETE | `/projects/{id}` | Delete project |
| POST | `/projects/{id}/images` | Upload project image |
| DELETE | `/projects/{id}/images/{imageId}` | Delete project image |

---

## Running locally

### Clone repository

```bash
git clone https://github.com/ronneyrv/portfolio-api.git

cd portfolio-api
```

### Environment variables

Create the environment variables required by the application:

```env
DB_URL=
DB_USERNAME=
DB_PASSWORD=

JWT_SECRET=

CLOUDINARY_CLOUD_NAME=
CLOUDINARY_API_KEY=
CLOUDINARY_API_SECRET=

ADMIN_USERNAME=
ADMIN_PASSWORD=
```

### Run application

```bash
./mvnw spring-boot:run
```

---

## Docker

### Build image

```bash
docker build -t portfolio-api .
```

### Run container

```bash
docker run -p 8080:8080 portfolio-api
```

---

## Deployment

The API is containerized with Docker and deployed to an Oracle Cloud VM.

The production environment uses:

- Docker
- PostgreSQL
- Nginx
- HTTPS
- JWT authentication
- Cloudinary for image storage

---

## Author

Ronney da Rocha Vieira