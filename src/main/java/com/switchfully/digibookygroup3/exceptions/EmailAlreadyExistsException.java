package com.switchfully.digibookygroup3.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email_address) {

        super("E-mail address " + email_address + " is already in use.");
    }
}
