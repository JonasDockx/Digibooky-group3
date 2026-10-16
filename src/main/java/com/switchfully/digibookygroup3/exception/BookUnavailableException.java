package com.switchfully.digibookygroup3.exception;

public class BookUnavailableException extends RuntimeException {
    public BookUnavailableException(String bookIsbn) {
        super("Book with isbn: " + bookIsbn + " is not available");
    }
}
