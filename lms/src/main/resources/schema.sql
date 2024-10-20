
CREATE TABLE roles (
                       role_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       role_name VARCHAR(20) NOT NULL UNIQUE
);


INSERT INTO roles (role_name) VALUES ('ROLE_USER'), ('ROLE_ADMIN');


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


CREATE INDEX idx_username ON users(username);
CREATE INDEX idx_email ON users(email);
CREATE INDEX idx_phoneNo ON users(phoneNo);
CREATE INDEX idx_isbn ON books(isbn);
CREATE INDEX idx_title ON books(title);
CREATE INDEX idx_author ON books(author);
author VARCHAR(100) NOT NULL,
                       category VARCHAR(50) NOT NULL,
                       available_copies INT NOT NULL,
                       published_year INT NOT NULL
);

-- Indexing for performance (optional but recommended)
CREATE INDEX idx_username ON users(username);
CREATE INDEX idx_email ON users(email);
CREATE INDEX idx_phoneNo ON users(phoneNo);
CREATE INDEX idx_isbn ON books(isbn);
CREATE INDEX idx_title ON books(title);
CREATE INDEX idx_author ON books(author);
