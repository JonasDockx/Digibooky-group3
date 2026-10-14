package com.switchfully.digibookygroup3.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(BookNotFoundException.class);

    public BookNotFoundException(String id) {
        super("Book not found with id " + id);
        logger.error("Book not found with id {}", id);
    }
}
