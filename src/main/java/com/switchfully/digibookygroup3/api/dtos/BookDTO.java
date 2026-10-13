package com.switchfully.digibookygroup3.api.dtos;

public class BookDTO {
    private final String id;
    private final String title;
    private final String isbn;
    private final AuthorDTO authorDTO;

    public BookDTO(String id, String title, String isbn, AuthorDTO authorDTO) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.authorDTO = authorDTO;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public AuthorDTO getAuthorDTO() {
        return authorDTO;
    }
}

