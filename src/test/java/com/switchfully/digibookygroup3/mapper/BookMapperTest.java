package com.switchfully.digibookygroup3.mapper;

import com.switchfully.digibookygroup3.api.dtos.AuthorDTO;
import com.switchfully.digibookygroup3.api.dtos.BookDTO;
import com.switchfully.digibookygroup3.api.dtos.BookWithSummaryDTO;
import com.switchfully.digibookygroup3.domain.book.Author;
import com.switchfully.digibookygroup3.domain.book.AuthorRepository;
import com.switchfully.digibookygroup3.domain.book.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {
    private BookMapper bookMapper = new BookMapper(new AuthorRepository());

    Author author = new Author("Test", "Test");
    Book book = new Book("123456789", "Test", author.getId(), "Test summary");

    @Test
    void testIfBookIsNull_DTOShouldBeNullToo() {
        BookDTO result = bookMapper.toDto(null, new AuthorDTO(author.getId(), author.getFirstname(), author.getLastname()));

        assertNull(result);
    }

    @Test
    void testIfAuthorIsNull_DTOShouldBeNullToo() {
        BookDTO result = bookMapper.toDto(book, null);

        assertNull(result);
    }

    @Test
    void testIfBookWithSummaryIsNull_DTOWithSummaryShouldBeNullToo() {
        BookWithSummaryDTO result = bookMapper.toDtoWithSummary(null, new AuthorDTO(author.getId(), author.getFirstname(), author.getLastname()));

        assertNull(result);
    }

    @Test
    void testIfAuthorIsNull_DTOWithSummaryShouldBeNullToo() {
        BookWithSummaryDTO result = bookMapper.toDtoWithSummary(book, null);

        assertNull(result);
    }
}