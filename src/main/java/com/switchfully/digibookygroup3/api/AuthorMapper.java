package com.switchfully.digibookygroup3.api;

import com.switchfully.digibookygroup3.api.dtos.AuthorDTO;
import com.switchfully.digibookygroup3.domain.book.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    public AuthorDTO toDto(Author author) {
        if (author == null) return null;

        return new AuthorDTO(
                author.getId(),
                author.getFirstname(),
                author.getLastname()
        );
    }
}
