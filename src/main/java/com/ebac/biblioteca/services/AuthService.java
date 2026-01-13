package com.ebac.biblioteca.services;


import com.ebac.biblioteca.exceptions.InvalidCredentialsException;
import com.ebac.biblioteca.entity.AdminSessionEntity;
import com.ebac.biblioteca.entity.AdminUserEntity;
import com.ebac.biblioteca.repository.AdminSessionRepository;
import com.ebac.biblioteca.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AdminUserRepository adminUserRepository;
    private final AdminSessionRepository adminSessionRepository;

    // LOGIN
    public Long login(String username, String password) {

        AdminUserEntity admin = adminUserRepository
                .findByUsernameAndPasswordAndActiveTrue(username, password)
                .orElseThrow(InvalidCredentialsException::new);

        AdminSessionEntity session = new AdminSessionEntity();
        session.setAdmin(admin);
        session.setLoginTime(LocalDateTime.now());
        session.setActive(true);

        return adminSessionRepository.save(session).getId();
    }

        public void logout(Long sessionId) {

            AdminSessionEntity session = adminSessionRepository
                    .findByIdAndActiveTrue(sessionId)
                    .orElseThrow(() -> new RuntimeException("Sesión inválida"));

            session.setActive(false);
            session.setLogoutTime(LocalDateTime.now());

            adminSessionRepository.save(session);
        }

        public void validateSession(Long sessionId) {

            adminSessionRepository
                    .findByIdAndActiveTrue(sessionId)
                    .orElseThrow(() -> new RuntimeException("Sesión no válida o expirada"));
        }
}
