package com.switchfully.digibookygroup3.service;

import com.switchfully.digibookygroup3.api.dtos.AuthorDTO;
import com.switchfully.digibookygroup3.api.dtos.BookDTO;
import com.switchfully.digibookygroup3.api.dtos.BookWithSummaryDTO;
import com.switchfully.digibookygroup3.domain.book.Author;
import com.switchfully.digibookygroup3.domain.book.AuthorRepository;
import com.switchfully.digibookygroup3.domain.book.Book;
import com.switchfully.digibookygroup3.domain.book.BookRepository;
import com.switchfully.digibookygroup3.exception.AuthorNotFoundException;
import com.switchfully.digibookygroup3.exception.BookNotFoundException;
import com.switchfully.digibookygroup3.mapper.AuthorMapper;
import com.switchfully.digibookygroup3.mapper.BookMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;
    private final AuthorMapper authorMapper;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, BookMapper bookMapper, AuthorMapper authorMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookMapper = bookMapper;
        this.authorMapper = authorMapper;
    }

    public List<BookDTO> getAllBooks() {
        logger.info("Received getAllBooks request");
        List<Book> books = bookRepository.getAllBooks();
        logger.debug("Fetched books with size {}", books.size());
        return books
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public BookWithSummaryDTO getBookById(String id) {
        logger.info("Received getBookById request with id {}", id);
        Book book = bookRepository.getBydId(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        return mapToDtoWithSummary(book);
    }

    private BookDTO mapToDto(Book book) {
//        In order to build the book DTO, we also need the author DTO. This function assembles
//        the book DTO and author DTO
        logger.info("Mapping a book to a BookDTO");
        return bookMapper.toDto(book, getAuthorDTOById(book.getAuthorId()));
    }

    private BookWithSummaryDTO mapToDtoWithSummary(Book book) {
        logger.info("Mapping a book to a BookWithSummaryDTO");
        return bookMapper.toDtoWithSummary(book, getAuthorDTOById(book.getAuthorId()));
    }

    private AuthorDTO getAuthorDTOById(String id) {
        logger.info("Received getAuthorDTOById request with id {}", id);
        Author author = authorRepository.getById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
        return authorMapper.toDto(author);
    }

    public List<BookDTO> getBookByISBN(String isbn) {
        logger.info("Received getBookByISBN request with ISBN {}", isbn);
        return bookRepository.getBooksByISBNNumber(isbn)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public List<BookDTO> getBookByTitle(String title) {
        logger.info("Received getBookByTitle request with title {}", title);
        return bookRepository.getBooksByTitle(title)
                .stream()
                .map(this::mapToDto)
                .toList();
    public List<BookDTO> searchBooksByAuthor(String name) {
        logger.info("Received search books by author request");
        if (name == null || name.isBlank()) {
            logger.warn("Not possible to fetch books for a blank name");
            throw new IllegalArgumentException("Author name must not be blank");
        }
        Set<String> authorIds = authorRepository.getAuthorsByName(name).stream()
                .map(Author::getId)
                .collect(Collectors.toSet());
        List<BookDTO> bookDTOS = bookRepository.getBooksByAuthorIds(authorIds).stream()
                .map(this::mapToDto)
                .toList();
        logger.info("Fetched books with size {}", bookDTOS.size());
        return bookDTOS;
    }
}
