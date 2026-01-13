package com.ebac.biblioteca.dto;

public class Author {
    private Long id;
    private String nombre;

    public Author() {
    }

    public Author(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}