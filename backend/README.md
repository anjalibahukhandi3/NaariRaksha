# NaariRaksha Backend API

Spring Boot backend for the NaariRaksha women's safety application.

## 🚀 Quick Start

### Local Development
```bash
./mvnw spring-boot:run
```

The server will start on `http://localhost:8080`

### Using Docker
```bash
docker build -t naariraksha-backend .
docker run -p 8080:8080 naariraksha-backend
```

## 📡 API Endpoints

- `POST /api/users/register` - Register a new user
- `POST /api/users/login` - User authentication  
- `POST /api/users/sos` - Send emergency SOS alert
- `GET /health` - Health check endpoint

## 🔧 Configuration

The application uses environment variables for configuration:

- `PORT` - Server port (default: 8080)
- `DB_URL` - Database JDBC URL (default: H2 in-memory)
- `DB_USER` - Database username
- `DB_PASSWORD` - Database password
- `DB_DRIVER` - JDBC driver class
- `DB_DIALECT` - Hibernate dialect

## 🗄️ Database

**Development**: Uses H2 in-memory database by default

**Production**: Supports PostgreSQL and MySQL via environment variables

## 📦 Tech Stack

- Java 17
- Spring Boot 3.2.2
- Spring Data JPA
- H2/PostgreSQL/MySQL
- Maven
