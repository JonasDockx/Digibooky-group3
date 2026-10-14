package com.switchfully.digibookygroup3.api.dtos;

import java.util.Objects;

public class BookWithSummaryDTO extends BookDTO {
    private final String summary;

    public BookWithSummaryDTO(String id, String title, String isbn, AuthorDTO authorDTO, String summary) {
        super(id, title, isbn, authorDTO);
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BookWithSummaryDTO that = (BookWithSummaryDTO) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getIsbn(), that.getIsbn()) &&
                Objects.equals(getAuthorDTO(), that.getAuthorDTO()) &&
                Objects.equals(getSummary(), that.getSummary());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getIsbn(), getAuthorDTO(), getSummary());
    }
}
