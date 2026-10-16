package com.switchfully.digibookygroup3.exception;

public class BookUnavailableException extends RuntimeException {
    public BookUnavailableException(String bookId) {
        super("Book with id " + bookId + " is not available");
    }
}
