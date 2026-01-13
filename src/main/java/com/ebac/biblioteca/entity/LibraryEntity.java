package com.ebac.biblioteca.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "libraries")
public class LibraryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @OneToMany
    @JoinColumn(name = "library_id")
    private List<BookEntity> books;

    public LibraryEntity() {}

    public LibraryEntity(String nombre) {
        this.nombre = nombre;
    }

}