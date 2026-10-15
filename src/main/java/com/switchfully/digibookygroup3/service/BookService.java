package com.switchfully.digibookygroup3.service;

import com.switchfully.digibookygroup3.api.AuthorMapper;
import com.switchfully.digibookygroup3.api.BookMapper;
import com.switchfully.digibookygroup3.api.dtos.AuthorDTO;
import com.switchfully.digibookygroup3.api.dtos.BookDTO;
import com.switchfully.digibookygroup3.domain.book.Author;
import com.switchfully.digibookygroup3.domain.book.AuthorRepository;
import com.switchfully.digibookygroup3.domain.book.Book;
import com.switchfully.digibookygroup3.domain.book.BookRepository;
import com.switchfully.digibookygroup3.exception.AuthorNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final Logger logger = LoggerFactory.getLogger(BookRepository.class);

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
        logger.info("Received get all books request");
        List<Book> books = bookRepository.getAllBooks();
        logger.debug("Fetched books with size {}", books.size());
        return books
                .stream()
                .map(book -> mapToDto(book, book.getAuthorId()))
                .toList();
    }

    private BookDTO mapToDto(Book book, String authorId) {
        AuthorDTO authorDTO = authorMapper.toDto(getAuthorById(authorId));
        return bookMapper.toDto(book, authorDTO);
    }

    private Author getAuthorById(String id) {
        return authorRepository.getById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
    }

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
                .map(book -> mapToDto(book, book.getAuthorId()))
                .toList();
        logger.info("Fetched books with size {}", bookDTOS.size());
        return  bookDTOS;
    }
}
