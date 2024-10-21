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
-- Books Table
CREATE TABLE books (
                       id               VARCHAR(255) NOT NULL,
                       author           VARCHAR(255) NOT NULL,
                       available_copies INTEGER NOT NULL,
                       category         VARCHAR(255) NOT NULL,
                       isbn             VARCHAR(255) NOT NULL,
                       published_year   INTEGER NOT NULL,
                       title            VARCHAR(255) NOT NULL,
                       PRIMARY KEY (id),
                       CONSTRAINT UC_Books_Title UNIQUE (title)
);

-- Roles Table
CREATE TABLE roles (
                       role_id   VARCHAR(255) NOT NULL,
                       role_name VARCHAR(20) CHECK (role_name IN ('ROLE_USER', 'ROLE_ADMIN')),
                       PRIMARY KEY (role_id)
);

-- Users Table
CREATE TABLE users (
                       user_id                    VARCHAR(255) NOT NULL,
                       account_expiry_date        DATE,
                       account_non_expired        BOOLEAN NOT NULL,
                       account_non_locked         BOOLEAN NOT NULL,
                       phone_no                   VARCHAR(255) NOT NULL,
                       created_at                 TIMESTAMP(6),
                       credentials_expiry_date    DATE,
                       credentials_non_expired    BOOLEAN NOT NULL,
                       email                      VARCHAR(255),
                       enabled                    BOOLEAN NOT NULL,
                       password                   VARCHAR(255) NOT NULL,
                       sign_up_method             VARCHAR(255),
                       updated_at                 TIMESTAMP(6),
                       username                   VARCHAR(255),
                       role_id                    VARCHAR(255),
                       PRIMARY KEY (user_id),
                       CONSTRAINT UC_Users_Username UNIQUE (username),
                       CONSTRAINT UC_Users_Email UNIQUE (email),
                       CONSTRAINT UC_Users_PhoneNo UNIQUE (phone_no),
                       CONSTRAINT FK_Users_Role FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

```




## Authors

- [Utkarsh Kumar Verma](https://github.com/Utkarshkverma)

