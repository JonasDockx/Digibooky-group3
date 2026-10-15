package com.switchfully.digibookygroup3.mapper;

import com.switchfully.digibookygroup3.api.dtos.AuthorDTO;
import com.switchfully.digibookygroup3.api.dtos.BookDTO;
import com.switchfully.digibookygroup3.api.dtos.BookWithSummaryDTO;
import com.switchfully.digibookygroup3.api.dtos.CreateBookDTO;
import com.switchfully.digibookygroup3.domain.book.Author;
import com.switchfully.digibookygroup3.domain.book.AuthorRepository;
import com.switchfully.digibookygroup3.domain.book.Book;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookMapper {
    private final AuthorRepository authorRepository;

    public BookMapper(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

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


    public Book mapDTOToBook(@Valid CreateBookDTO dto, String authorId) {

        return new Book(
                dto.getIsbn(),
                dto.getTitle(),
                authorId,
                dto.getSummary()
        );
    }
}
