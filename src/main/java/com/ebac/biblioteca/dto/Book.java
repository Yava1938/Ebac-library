package com.ebac.biblioteca.dto;

import java.util.Objects;

public class Book {

    private int id_libro;
    private String nombre_libro;
    private int id_autor;
    private int anio;
    private boolean disponible =  true;
    private Integer id_usuario;

    public Book() {
    }

    public Book(String nombre_libro, int id_autor, int anio, boolean disponible, Integer id_usuario) {
        this.nombre_libro = nombre_libro;
        this.id_autor = id_autor;
        this.anio = anio;
        this.disponible = disponible;
        this.id_usuario = id_usuario;
    }

    public int getId_libro() {
        return id_libro;
    }

    public void setId_libro(int id_libro) {
        this.id_libro = id_libro;
    }

    public String getNombre_libro() {
        return nombre_libro;
    }

    public void setNombre_libro(String nombre_libro) {
        this.nombre_libro = nombre_libro;
    }

    public int getId_autor() {
        return id_autor;
    }

    public void setId_autor(int id_autor) {
        this.id_autor = id_autor;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id_libro=" + id_libro +
                ", nombre_libro='" + nombre_libro + '\'' +
                ", id_autor=" + id_autor +
                ", anio=" + anio +
                ", disponible=" + disponible +
                ", id_usuario=" + id_usuario +
                '}';
    }
}
