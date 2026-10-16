package com.switchfully.digibookygroup3.api.dtos;

public class CreateLoanDTO {
    private String bookIsbn;
    private String userId;

    public CreateLoanDTO(String bookIsbn, String userId) {
        this.bookIsbn = bookIsbn;
        this.userId = userId;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public String getUserId() {
        return userId;
    }
}