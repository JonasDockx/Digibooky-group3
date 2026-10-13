package com.switchfully.digibookygroup3.api;

import com.switchfully.digibookygroup3.api.dtos.BookDTO;
import com.switchfully.digibookygroup3.domain.book.Author;
import com.switchfully.digibookygroup3.domain.book.Book;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookMapper {
    private final AuthorMapper authorMapper;

    public BookMapper(AuthorMapper authorMapper) {
        this.authorMapper = authorMapper;
    }

    public List<BookDTO> toDto(List<Book> books) {
        books.stream().map(this::toDto)
    }

    public BookDTO toDto(Book book, Author author) {
        if (book == null || author == null) {
            return null;
        }

        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getISBN(),
                authorMapper.toDto(author)
        );
    }
}
