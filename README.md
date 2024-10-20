# Library Management System

## Overview

Build a simple Library Management System (LMS) REST API using Spring Boot. This system will help manage books and member information in a library.

## Table of Contents

- [Assumptions](#assumptions)
- [Setup Instructions](#setup-instructions)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)
- [Authors](#authors)

## Assumptions

1. Admin Role: Users logging in with an email suffix of `proton.me` are assigned the role of Admin.

2. Member Update Restrictions:
 - The endpoint `PUT /api/v1/members/{id}` is restricted to Admins.
 - Admins can only update the role of users, not their entire information. 

3. API Path Adjustments:
 - User registration is at `POST /api/v1/auth/public/sign-up`.
 - Login is at `POST /api/v1/auth/public/sign-in`.
 - Get current user's name at `GET /api/v1/auth/username`.

4. Pagination: The default number of entities per page is set to 10.


## Setup Instructions



1. Clone the repository
    ```bash
   git clone https://github.com/Utkarshkverma/crezam-lms.git
   cd lms
   
2. Database Configuration:
  - If you have PostgreSQL installed, update `src/main/resources/application-dev.properties`:
  ```bash
spring.datasource.username=<your-username>
spring.datasource.password=<your-password>
spring.datasource.url=jdbc:postgresql://localhost:5432/<your-database>
```
 - Alternatively, use Docker:
   
   - Create a Docker account and install Docker Desktop.
   - Run the Docker Compose file in the repository:

     ``` bash
     docker compose up -d
     ```
3. Run the Application:
- Use your IDE to run the Spring Boot application or run the following command:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Access the Application:

- The application will be accessible at `http://localhost:7070/api/v1`


## API Documentation
Once the application is running, you can access the API documentation by visiting:
 - [Api Documentation](http://localhost:7070/api/v1/swagger-ui/index.html)

## Database Schema

### SQL Script

The following SQL script creates the schema for the `users`, `books`, and `roles` tables:
```bash
-- Create roles table
CREATE TABLE roles (
    role_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    role_name VARCHAR(20) NOT NULL UNIQUE
);

-- Insert default roles
INSERT INTO roles (role_name) VALUES ('ROLE_USER'), ('ROLE_ADMIN');

-- Create users table
CREATE TABLE users (
    user_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(50) NOT NULL UNIQUE,
    phoneNo VARCHAR(15) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    account_non_locked BOOLEAN DEFAULT TRUE,
    account_non_expired BOOLEAN DEFAULT TRUE,
    credentials_non_expired BOOLEAN DEFAULT TRUE,
    enabled BOOLEAN DEFAULT TRUE,
    credentials_expiry_date DATE,
    account_expiry_date DATE,
    sign_up_method VARCHAR(50),
    role_id UUID,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE SET NULL
);

-- Create books table
CREATE TABLE books (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    isbn VARCHAR(20) NOT NULL UNIQUE,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    available_copies INT NOT NULL,
    published_year INT NOT NULL
);

-- Indexing for performance
CREATE INDEX idx_username ON users(username);
CREATE INDEX idx_email ON users(email);
CREATE INDEX idx_phoneNo ON users(phoneNo);
CREATE INDEX idx_isbn ON books(isbn);
CREATE INDEX idx_title ON books(title);
CREATE INDEX idx_author ON books(author);
```




## Authors

- [Utkarsh Kumar Verma](https://github.com/Utkarshkverma)

