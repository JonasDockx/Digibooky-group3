package com.switchfully.digibookygroup3.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {

        super("Wrong input");
    }
}
