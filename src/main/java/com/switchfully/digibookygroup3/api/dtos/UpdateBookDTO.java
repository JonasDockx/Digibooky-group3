package com.switchfully.digibookygroup3.api.dtos;

public record UpdateBookDTO(String title, String isbn, String summary, AuthorDTO authorDTO) {
}
