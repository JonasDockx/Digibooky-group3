package com.switchfully.digibookygroup3.api;

import com.switchfully.digibookygroup3.api.dtos.BookDTO;
import com.switchfully.digibookygroup3.api.dtos.BookWithSummaryDTO;
import com.switchfully.digibookygroup3.api.dtos.UpdateBookDTO;
import com.switchfully.digibookygroup3.service.BookService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/books")
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

    @GetMapping(path = "/search", produces = APPLICATION_JSON, params = "isbn")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDTO> getBooksByISBN(@PathParam("isbn") String isbn) {
        return bookService.getBookByISBN(isbn);
    }

    @GetMapping(path = "/search", produces = APPLICATION_JSON, params = "title")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDTO> getBooksByTitle(@PathParam("title") String title) {
        return bookService.getBookByTitle(title);
    }


    @GetMapping(path = "/search", produces = APPLICATION_JSON, params = "authorname")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDTO> getBooksByAuthorName(@PathParam("authorname") String name) {
        return bookService.searchBooksByAuthor(name);
    }
    @PutMapping(path = "/{id}", produces = APPLICATION_JSON, consumes = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public BookWithSummaryDTO updateBook(@RequestBody UpdateBookDTO updateBookDTO, @PathVariable("id") String id) {
        return bookService.updateBook(updateBookDTO, id);
    }

}
