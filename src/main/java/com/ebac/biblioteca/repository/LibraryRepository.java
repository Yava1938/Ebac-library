package com.ebac.biblioteca.repository;

import com.ebac.biblioteca.entity.LibraryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<LibraryEntity, Long> {
}
