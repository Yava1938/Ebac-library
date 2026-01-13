package com.ebac.biblioteca.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ResponseWrapper<T> {

    private int statusCode;
    private String contexto;
    private List<String> errors;
    private T resultado;

    public ResponseWrapper(int statusCode, String contexto, List<String> errors, T resultado) {
        this.statusCode = statusCode;
        this.contexto = contexto;
        this.errors = errors;
        this.resultado = resultado;
    }
}