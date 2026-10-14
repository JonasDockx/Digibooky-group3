package com.switchfully.digibookygroup3.api.dtos;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BookDTO bookDTO = (BookDTO) o;
        return Objects.equals(id, bookDTO.id) && Objects.equals(title, bookDTO.title) && Objects.equals(isbn, bookDTO.isbn) && Objects.equals(authorDTO, bookDTO.authorDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, isbn, authorDTO);
    }
}

