package com.switchfully.digibookygroup3.api;

import com.switchfully.digibookygroup3.api.dtos.BookDTO;
import com.switchfully.digibookygroup3.api.dtos.BookWithSummaryDTO;
import com.switchfully.digibookygroup3.domain.book.Author;
import com.switchfully.digibookygroup3.domain.book.AuthorRepository;
import com.switchfully.digibookygroup3.domain.book.Book;
import com.switchfully.digibookygroup3.domain.book.BookRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    Book book;

    @BeforeEach
    void setUp() {
    }

    private void addBooksAndAuthors() {
        Author author = new Author("Jonas", "Dockx");
        book = new Book("123456789", "Test", author.getId(), "Test test test summary");

        authorRepository.addAuthor(author);
        bookRepository.addBook(book);
    }

    private BookDTO[] getAllBooks() {
        return RestAssured.given()
                .port(port)
                .get("/books")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().as(BookDTO[].class);
    }

    private BookWithSummaryDTO showBookDetails() {
        return RestAssured.given()
                .port(port)
                .get("/books/" + book.getId())
                .then().statusCode(HttpStatus.OK.value())
                .extract().as(BookWithSummaryDTO.class);
    }

    private String showBookDetailsForNonExistingBook() {
        return RestAssured.given()
                .port(port)
                .get("/books/123")
                .then().statusCode(HttpStatus.NOT_FOUND.value())
                .extract().asString();
    }

    @Test
    void getAllBooksReturnsAllBooksInARepository() {
        int before = getAllBooks().length;
        addBooksAndAuthors();

        assertEquals(before + 1, getAllBooks().length);
    }

    @Test
    void getBookById() {
        addBooksAndAuthors();

        BookWithSummaryDTO bookWithSummaryDTO = showBookDetails();

        assertEquals(book.getId(), bookWithSummaryDTO.getId());
    }

    @Test
    void getBookByIdThrowsExceptionIfBookIsNotPresent() {
        String errorMessage = showBookDetailsForNonExistingBook();

        assertEquals("Book not found with id 123", errorMessage);
    }

    @Test
    void getBookByISBNReturnsTheBook() {
        addBooksAndAuthors();
        Author author = new Author("Test", "Test");
        authorRepository.addAuthor(author);
        bookRepository.addBook(new Book("987654321", "Test", author.getId(), "Test summary"));

        BookDTO[] filteredBooks = RestAssured.given()
                .port(port)
                .get("/books?isbn=*543*")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().as(BookDTO[].class);

        assertEquals(1, filteredBooks.length);
    }

    @Test
    void getBookByISBNReturnsEmptyListWhenISBNIsNotPresent() {
        addBooksAndAuthors();

        BookDTO[] filteredBooks = RestAssured.given()
                .port(port)
                .get("/books?isbn=*543*")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().as(BookDTO[].class);

        assertEquals(0, filteredBooks.length);
    }
}