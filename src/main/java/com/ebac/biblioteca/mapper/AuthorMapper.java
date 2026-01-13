package com.ebac.biblioteca.mapper;

import com.ebac.biblioteca.dto.Author;
import com.ebac.biblioteca.entity.AuthorEntity;

public class AuthorMapper {

    public static Author toDto(AuthorEntity entity) {
        if (entity == null) return null;

        Author dto = new Author();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        return dto;
    }

    public static AuthorEntity toEntity(Author dto) {
        if (dto == null) return null;

        AuthorEntity entity = new AuthorEntity();
        entity.setNombre(dto.getNombre());
        return entity;
    }
}