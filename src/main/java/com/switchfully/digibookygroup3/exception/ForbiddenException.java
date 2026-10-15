package com.switchfully.digibookygroup3.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException() {
        super("Access to the resource is forbidden");
    }
}
