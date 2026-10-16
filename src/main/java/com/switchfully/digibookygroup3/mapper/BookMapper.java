package com.switchfully.digibookygroup3.mapper;

import com.switchfully.digibookygroup3.api.dtos.*;
import com.switchfully.digibookygroup3.domain.book.Book;
import jakarta.validation.Valid;
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

    public BookWithAllDetailsDTO toDtoWithAllDetails(Book book, AuthorDTO authorDto, String userId) {
        if (book == null || authorDto == null) {
            return null;
        }

        return new BookWithAllDetailsDTO(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                authorDto,
                book.getSummary(),
                userId
        );
    }


    public Book mapDTOToBook(@Valid CreateBookDTO dto, String authorId) {

        return new Book(
                dto.getIsbn(),
                dto.getTitle(),
                authorId,
                dto.getSummary()
        );
    }
}
