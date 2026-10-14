package com.switchfully.digibookygroup3.service;

import com.switchfully.digibookygroup3.mapper.AuthorMapper;
import com.switchfully.digibookygroup3.mapper.BookMapper;
import com.switchfully.digibookygroup3.api.dtos.AuthorDTO;
import com.switchfully.digibookygroup3.api.dtos.BookDTO;
import com.switchfully.digibookygroup3.domain.book.Author;
import com.switchfully.digibookygroup3.domain.book.AuthorRepository;
import com.switchfully.digibookygroup3.domain.book.Book;
import com.switchfully.digibookygroup3.domain.book.BookRepository;
import com.switchfully.digibookygroup3.exception.AuthorNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class BookServiceTest {
    private BookService bookService;
    private BookRepository bookRepositoryMock;
    private AuthorRepository authorRepositoryMock;

    @BeforeEach
    void setup() {
        bookRepositoryMock = mock(BookRepository.class);
        authorRepositoryMock = mock(AuthorRepository.class);
        bookService = new BookService(bookRepositoryMock, authorRepositoryMock,
                new BookMapper(), new AuthorMapper());
    }

    @Test
    void getAll_givenFilledRepositories_whenGetAllBooks_thenReturnsAllBookDTOs() {
        List<Author> authors = new LinkedList<>(List.of(new Author("a1_firstname", "a1_lastname"),
                new Author("a2_firstname", "a2_lastname")));
        String firstAuthorId = authors.get(0).getId();
        String secondAuthorId = authors.get(1).getId();

        List<Book> books = new LinkedList<>(List.of(new Book("ISBN1", "title1", firstAuthorId),
                new Book("ISBN2", "title2", secondAuthorId)));
        when(bookRepositoryMock.getAllBooks())
                .thenReturn(books);
        when(authorRepositoryMock.getById(firstAuthorId))
                .thenReturn(Optional.of(authors.get(0)));
        when(authorRepositoryMock.getById(secondAuthorId))
                .thenReturn(Optional.of(authors.get(1)));

        List<BookDTO> bookDTOS = bookService.getAllBooks();

        verify(bookRepositoryMock).getAllBooks();
        verify(authorRepositoryMock).getById(firstAuthorId);
        verify(authorRepositoryMock).getById(secondAuthorId);

        assertThat(bookDTOS).isNotEmpty().hasSize(2);
        assertThat(bookDTOS.get(0).getId()).isNotEmpty();
        assertThat(bookDTOS).extracting(BookDTO::getIsbn).containsOnly("ISBN1", "ISBN2");
        assertThat(bookDTOS)
                .extracting(BookDTO::getAuthorDTO)
                .extracting(AuthorDTO::firstname)
                .containsOnly("a1_firstname", "a2_firstname");
    }

    @Test
    void getAll_givenBooksWithInvalidAuthorId_whenGetAllBooks_thenThrowsAuthorNotFoundException() {
        List<Book> books = new LinkedList<>(List.of(new Book("ISBN1", "title1", "invalidId")));

        when(bookRepositoryMock.getAllBooks())
                .thenReturn(books);

        assertThatThrownBy(() -> bookService.getAllBooks())
                .isInstanceOf(AuthorNotFoundException.class)
                        .hasMessage("Author not found with id invalidId");
    }

    @Test
    void getBookById_givenAValidIdOfBookWithSummary_whenGetBookById_thenReturnBookDtoWithAllDetailsOfTheBook() {
        throw new UnsupportedOperationException();
    }

    @Test
    void getBookById_givenAValidIdOfBookWithoutSummary_whenCalled_thenDisplayAnEmptyValueForTheSummary() {
        throw new UnsupportedOperationException();
    }

    }

    @Test
    void getBookById_givenAnInvalidId_whenCalled_thenReturnHttoNotFoundError() {
        throw new UnsupportedOperationException();
    }