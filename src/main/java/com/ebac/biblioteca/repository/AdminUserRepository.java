package com.ebac.biblioteca.repository;

import com.ebac.biblioteca.entity.AdminUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminUserRepository extends JpaRepository<AdminUserEntity, Long> {

    Optional<AdminUserEntity> findByUsernameAndPasswordAndActiveTrue(
            String username, String password
    );
}
