package com.ebac.biblioteca.repository;

import com.ebac.biblioteca.entity.AdminSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminSessionRepository extends JpaRepository<AdminSessionEntity, Long> {

    Optional<AdminSessionEntity> findByIdAndActiveTrue(Long id);
}
