drop database if exists biblioteca;

create database if not exists biblioteca;
use biblioteca;

create table if not exists usuarios(
    id_usuario int not null auto_increment primary key,
    nombre_usuario varchar(100) not null,
    email_usuario varchar(100),
    password_usuario varchar(100),
    fecha_registro timestamp default (current_date)
);

create table if not exists autores(
    id_autor int not null auto_increment primary key,
    nombre_autor varchar(40),
    nacionalidad varchar(50)
);

create table if not exists libros(
    id_libro int not null auto_increment primary key,
    nombre_libro varchar(100),
    id_autor int,
    anio_publicacion int,
    disponible boolean default true,
    id_usuario int
);

