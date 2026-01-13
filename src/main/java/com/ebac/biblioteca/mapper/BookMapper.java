package com.ebac.biblioteca.mapper;

import com.ebac.biblioteca.dto.Book;
import com.ebac.biblioteca.entity.AuthorEntity;
import com.ebac.biblioteca.entity.BookEntity;
import com.ebac.biblioteca.entity.LibraryEntity;

public class BookMapper {

    public static BookEntity toEntity(Book dto, AuthorEntity author, LibraryEntity library) {
        BookEntity entity = new BookEntity();
        entity.setNombre(dto.getNombre());
        entity.setAnio(dto.getAnio());
        entity.setDisponible(true);
        entity.setAuthor(author);
        entity.setLibrary(library);
        return entity;
    }

    public static Book toDto(BookEntity entity) {
        Book dto = new Book();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setAnio(entity.getAnio());
        dto.setDisponible(entity.isDisponible());
        dto.setAuthorId(entity.getAuthor() != null ? entity.getAuthor().getId() : null);
        dto.setUserId(entity.getUser() != null ? entity.getUser().getId() : null);
        dto.setLibraryId(entity.getLibrary() != null ? entity.getLibrary().getId() : null);
        return dto;
    }
}