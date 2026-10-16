package com.switchfully.digibookygroup3.exception;

public class LoanNotFoundException extends RuntimeException {
    public LoanNotFoundException(String id) {
        super("Loan not found with id: " + id);
    }
}
