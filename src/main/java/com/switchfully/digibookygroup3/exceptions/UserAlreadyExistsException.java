package com.switchfully.digibookygroup3.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {

        super("Wrong input");
    }
}
