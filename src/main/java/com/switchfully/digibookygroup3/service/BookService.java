package com.switchfully.digibookygroup3.service;

import com.switchfully.digibookygroup3.api.BookMapper;
import com.switchfully.digibookygroup3.api.dtos.BookDTO;
import com.switchfully.digibookygroup3.domain.book.AuthorRepository;
import com.switchfully.digibookygroup3.domain.book.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookMapper = bookMapper;
    }

    public List<BookDTO> getAllBooks() {

        return bookRepository.getAllBooks();
    }
}
