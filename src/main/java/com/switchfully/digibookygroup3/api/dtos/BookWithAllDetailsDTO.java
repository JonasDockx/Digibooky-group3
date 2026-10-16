package com.switchfully.digibookygroup3.api.dtos;


public class BookWithAllDetailsDTO extends BookWithSummaryDTO {
    private final String borrowerId;

    public BookWithAllDetailsDTO(String id, String title, String isbn, AuthorDTO authorDTO, String summary, String memberId) {
        super(id, title, isbn, authorDTO, summary);
        this.borrowerId = memberId;
    }

    public String getBorrowerId() {
        return borrowerId;
    }

}
