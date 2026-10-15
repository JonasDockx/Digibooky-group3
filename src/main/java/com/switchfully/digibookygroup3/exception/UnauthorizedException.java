package com.switchfully.digibookygroup3.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("Invalid credentials");
    }
}
