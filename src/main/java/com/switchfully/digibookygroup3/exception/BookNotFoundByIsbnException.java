package com.switchfully.digibookygroup3.exception;

public class BookNotFoundByIsbnException extends RuntimeException {
    public BookNotFoundByIsbnException(String isbn) {
        super("Book not found with isbn " + isbn);
    }
}
