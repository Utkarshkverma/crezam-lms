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
