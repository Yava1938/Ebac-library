package com.ebac.biblioteca.repository;

import com.ebac.biblioteca.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
