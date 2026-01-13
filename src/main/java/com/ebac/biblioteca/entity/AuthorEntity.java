package com.ebac.biblioteca.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "authors")
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "author")
    private List<BookEntity> books;

    public AuthorEntity() {}

    public AuthorEntity(String nombre) {
        this.nombre = nombre;
    }

}