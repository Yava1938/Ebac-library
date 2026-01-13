package com.ebac.biblioteca.repository;

import com.ebac.biblioteca.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
