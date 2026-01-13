package com.ebac.biblioteca.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "admin_sessions")
@Getter
@Setter
public class AdminSessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private AdminUserEntity admin;

    private LocalDateTime loginTime;

    private LocalDateTime logoutTime;

    private boolean active = true;
}
