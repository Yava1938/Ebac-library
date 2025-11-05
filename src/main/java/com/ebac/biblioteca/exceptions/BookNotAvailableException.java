package com.ebac.biblioteca.exceptions;

public class BookNotAvailableException extends RuntimeException{
    public BookNotAvailableException(String msg) {  super(msg); }
}
