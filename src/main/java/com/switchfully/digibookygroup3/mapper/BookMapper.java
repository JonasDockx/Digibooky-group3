package com.switchfully.digibookygroup3.mapper;

import com.switchfully.digibookygroup3.api.dtos.AuthorDTO;
import com.switchfully.digibookygroup3.api.dtos.BookDTO;
import com.switchfully.digibookygroup3.api.dtos.BookWithSummaryDTO;
import com.switchfully.digibookygroup3.domain.book.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookDTO toDto(Book book, AuthorDTO authorDto) {
        if (book == null || authorDto == null) {
            return null;
        }

        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                authorDto
        );
    }

    public BookWithSummaryDTO toDtoWithSummary(Book book, AuthorDTO authorDto) {
        if (book == null || authorDto == null) {
            return null;
        }

        return new BookWithSummaryDTO(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                authorDto,
                book.getSummary()
        );
    }

}
