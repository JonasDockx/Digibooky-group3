package com.switchfully.digibookygroup3.api.dtos;

public class BookWithSummaryDTO extends BookDTO {
    private final String summary;

    public BookWithSummaryDTO(String id, String title, String isbn, AuthorDTO authorDTO, String summary) {
        super(id, title, isbn, authorDTO);
        this.summary = summary;
    }

}
