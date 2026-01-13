drop database if exists biblioteca;
create database if not exists biblioteca;
use biblioteca;

create table if not exists users (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   nombre VARCHAR(150) NOT NULL UNIQUE
);

create table if not exists authors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL
);

CREATE TABLE libraries (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL
);

create table if not exists books (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   nombre VARCHAR(200) NOT NULL,
   anio INT NOT NULL,
   disponible BOOLEAN NOT NULL DEFAULT TRUE,
   author_id BIGINT NOT NULL,
   library_id BIGINT NOT NULL,
   user_id BIGINT NULL,
   CONSTRAINT fk_books_author
       FOREIGN KEY (author_id) REFERENCES authors(id),
   CONSTRAINT fk_books_library
       FOREIGN KEY (library_id) REFERENCES libraries(id),
   CONSTRAINT fk_books_user
       FOREIGN KEY (user_id) REFERENCES users(id)
);


CREATE TABLE admin_users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE admin_sessions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    admin_id BIGINT NOT NULL,
    login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    logout_time TIMESTAMP NULL,
    active BOOLEAN DEFAULT TRUE,

    CONSTRAINT fk_admin_session
        FOREIGN KEY (admin_id) REFERENCES admin_users(id)
);


INSERT INTO admin_users (username, password)
VALUES ('admin', 'admin123');

INSERT INTO libraries (nombre)
VALUES ('Biblioteca Central');