package com.switchfully.digibookygroup3.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookNotFoundException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(BookNotFoundException.class);

    public BookNotFoundException(String id) {
        super("Book not found with id " + id);
    }
}
