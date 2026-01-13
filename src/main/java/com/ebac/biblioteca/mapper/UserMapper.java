package com.ebac.biblioteca.mapper;

import com.ebac.biblioteca.dto.User;
import com.ebac.biblioteca.entity.UserEntity;

public class UserMapper {

    public static User toDto(UserEntity entity) {
        if (entity == null) return null;

        User dto = new User();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        return dto;
    }

    public static UserEntity toEntity(User dto) {
        if (dto == null) return null;

        UserEntity entity = new UserEntity();
        entity.setNombre(dto.getNombre());
        return entity;
    }
}