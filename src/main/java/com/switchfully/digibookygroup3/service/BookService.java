package com.switchfully.digibookygroup3.service;

import com.switchfully.digibookygroup3.api.AuthorMapper;
import com.switchfully.digibookygroup3.api.BookMapper;
import com.switchfully.digibookygroup3.api.dtos.AuthorDTO;
import com.switchfully.digibookygroup3.api.dtos.BookDTO;
import com.switchfully.digibookygroup3.domain.book.Author;
import com.switchfully.digibookygroup3.domain.book.AuthorRepository;
import com.switchfully.digibookygroup3.domain.book.Book;
import com.switchfully.digibookygroup3.domain.book.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
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
        List<Book> books = bookRepository.getAllBooks();
        return books
                .stream()
                .map(book -> mapToDto(book, book.getAuthorId()))
                .toList();
    }

    private BookDTO mapToDto(Book book, String authorId) {
        AuthorDTO authorDTO = getAuthorById(authorId);
        return bookMapper.toDto(book, authorDTO);
    }

    private AuthorDTO getAuthorById(String id) {
        Author author = authorRepository.getById(id)
                .orElseThrow(IllegalArgumentException::new);
        return authorMapper.toDto(author);
    }
}
