package com.switchfully.digibookygroup3.service;

import com.switchfully.digibookygroup3.api.dtos.AuthorDTO;
import com.switchfully.digibookygroup3.api.dtos.BookDTO;
import com.switchfully.digibookygroup3.api.dtos.BookWithSummaryDTO;
import com.switchfully.digibookygroup3.api.dtos.UpdateBookDTO;
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
        Book book = getBookByIdOrElse(id);

        return mapToDtoWithSummary(book);
    }

    private BookDTO mapToDto(Book book) {
//        In order to build the book DTO, we also need the author DTO. This function assembles
//        the book DTO and author DTO
        AuthorDTO authorDTO = authorMapper.toDto(getAuthorById(book.getAuthorId()));
        return bookMapper.toDto(book, authorDTO);
    }

    private BookWithSummaryDTO mapToDtoWithSummary(Book book) {
        AuthorDTO authorDTO = authorMapper.toDto(getAuthorById(book.getAuthorId()));
        return bookMapper.toDtoWithSummary(book, authorDTO);
    }

    private Author getAuthorById(String id) {
        return authorRepository.getById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
    }

    private Book getBookByIdOrElse(String id) {
        return bookRepository.getBydId(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public List<BookDTO> getBookByISBN(String isbn) {
        return bookRepository.getBooksByISBNNumber(isbn)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public BookWithSummaryDTO updateBook(UpdateBookDTO updateBookDTO, String id) {
        logger.info("Received update a book request with id {}", id);
        Author author = updateAuthor(updateBookDTO.authorDTO(), updateBookDTO.authorDTO().id());
        Book book = getBookByIdOrElse(id);

        book.setAuthorId(author.getId());
        book.setIsbn(updateBookDTO.isbn());
        book.setTitle(updateBookDTO.title());
        book.setSummary(updateBookDTO.summary());

        BookWithSummaryDTO updatedBook = mapToDtoWithSummary(bookRepository.save(book));
        logger.info("Book updated with id {}", id);

        return updatedBook;
    }

    private Author updateAuthor(AuthorDTO authorDTO, String id) {
        Author author = getAuthorById(id);
        author.setFirstname(authorDTO.firstname());
        author.setLastname(authorDTO.lastname());
        return authorRepository.save(author);
    }
}
