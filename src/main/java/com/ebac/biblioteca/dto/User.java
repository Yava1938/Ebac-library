package com.ebac.biblioteca.dto;

import java.util.Date;

public class User {

    private int id_usuario;
    private String nombre_usuario;
    private String email_usuario;
    private String password_usuario;
    private java.sql.Date fecha_registro;

    public User() {
    }

    public User(String nombre_usuario, String email_usuario, String password_usuario) {
        this.nombre_usuario = nombre_usuario;
        this.email_usuario = email_usuario;
        this.password_usuario = password_usuario;
        this.fecha_registro = new java.sql.Date(System.currentTimeMillis());
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getEmail_usuario() {
        return email_usuario;
    }

    public void setEmail_usuario(String email_usuario) {
        this.email_usuario = email_usuario;
    }

    public String getPassword_usuario() {
        return password_usuario;
    }

    public void setPassword_usuario(String password_usuario) {
        this.password_usuario = password_usuario;
    }

    public java.sql.Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(java.sql.Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    @Override
    public String toString() {
        return "User{" +
                "id_usuario=" + id_usuario +
                ", nombre_usuario='" + nombre_usuario + '\'' +
                ", email_usuario='" + email_usuario + '\'' +
                ", password_usuario='" + password_usuario + '\'' +
                ", fecha_registro=" + fecha_registro +
                '}';
    }
}
