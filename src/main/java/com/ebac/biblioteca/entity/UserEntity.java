package com.ebac.biblioteca.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @OneToMany(mappedBy = "user")
    private List<BookEntity> borrowedBooks;

    public UserEntity() {}

    public UserEntity(String nombre) {
        this.nombre = nombre;
    }

}