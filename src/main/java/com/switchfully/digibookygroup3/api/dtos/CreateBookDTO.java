package com.switchfully.digibookygroup3.api.dtos;

import jakarta.validation.constraints.NotBlank;

public class CreateBookDTO {
    @NotBlank
    private final String isbn;
    @NotBlank
    private final String title;
    @NotBlank
    private final String authorLastname;
    @NotBlank
    private final String authorFirstname;
    @NotBlank
    private final String summary;

    public CreateBookDTO(String isbn, String title, String authorLastname, String authorFirstname, String summary) {
        this.isbn = isbn;
        this.title = title;
        this.authorLastname = authorLastname;
        this.authorFirstname = authorFirstname;
        this.summary = summary;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getAuthorLastname() {
        return authorLastname;
    }

    public String getAuthorFirstname() {
        return authorFirstname;
    }
}
