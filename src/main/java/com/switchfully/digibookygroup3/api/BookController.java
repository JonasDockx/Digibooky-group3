package com.switchfully.digibookygroup3.api;

import com.switchfully.digibookygroup3.api.dtos.BookDTO;
import com.switchfully.digibookygroup3.api.dtos.BookWithSummaryDTO;
import com.switchfully.digibookygroup3.service.BookService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/books")
@RestController
public class BookController {
    private static final String APPLICATION_JSON = "application/json";

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public BookWithSummaryDTO showBookDetails(@PathVariable("id") String id) {
        return bookService.getBookById(id);
    }

    @GetMapping(path = "/search", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public List<BookDTO> getBooksByISBN(@PathParam("isbn") String isbn) {
        return bookService.getBookByISBN(isbn);
    }

    @GetMapping(path = "/search", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDTO> getBooksByAuthorName(@PathParam("authorname") String name) {
        return bookService.searchBooksByAuthor(name);
    }
}
