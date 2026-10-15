package com.switchfully.digibookygroup3.exception;

public class AuthorNotFoundException extends RuntimeException {

    public AuthorNotFoundException(String id) {
        super("Author not found with id " + id);
    }
}
