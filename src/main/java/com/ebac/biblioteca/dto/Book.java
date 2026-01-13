package com.ebac.biblioteca.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {

    private Long id;
    private String nombre;
    private int anio;
    private boolean disponible;
    private Long authorId;
    private Long userId;
    private Long libraryId;


    public Book() {
    }

    public Book(Long id, String nombre, int anio, boolean disponible, Long authorId, Long userId) {
        this.id = id;
        this.nombre = nombre;
        this.anio = anio;
        this.disponible = disponible;
        this.authorId = authorId;
        this.userId = userId;

    }




}
