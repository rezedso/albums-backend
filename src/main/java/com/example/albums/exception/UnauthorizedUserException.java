package com.example.albums.exception;

public class UnauthorizedUserException extends RuntimeException {
    public UnauthorizedUserException(String message){
        super(message);
    }
}

