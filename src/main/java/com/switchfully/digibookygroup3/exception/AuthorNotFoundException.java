package com.switchfully.digibookygroup3.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthorNotFoundException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(AuthorNotFoundException.class);

    public AuthorNotFoundException(String id) {
        super("Author not found with id " + id);
        logger.error("Author not found with id {}", id);
    }
}
