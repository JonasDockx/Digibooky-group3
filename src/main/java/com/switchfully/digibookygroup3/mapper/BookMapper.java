package com.switchfully.digibookygroup3.mapper;

import com.switchfully.digibookygroup3.api.dtos.*;
import com.switchfully.digibookygroup3.domain.book.Author;
import com.switchfully.digibookygroup3.domain.book.AuthorRepository;
import com.switchfully.digibookygroup3.domain.book.Book;
import com.switchfully.digibookygroup3.domain.loan.LoanRepository;
import com.switchfully.digibookygroup3.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookMapper {
    private final AuthorRepository authorRepository;
    private final LoanRepository loanRepository;

    public BookMapper(AuthorRepository authorRepository, LoanRepository loanRepository) {
        this.authorRepository = authorRepository;
        this.loanRepository = loanRepository;
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

    public BookWithAllDetailsDTO toDtoWithAllDetails(Book book, AuthorDTO authorDto) {
        if (book == null || authorDto == null) {
            return null;
        }

        return new BookWithAllDetailsDTO(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                authorDto,
                book.getSummary(),
                loanRepository.getUserIdByBookId(book.getId())
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
