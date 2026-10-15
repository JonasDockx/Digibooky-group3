package com.switchfully.digibookygroup3.api;

import com.switchfully.digibookygroup3.api.dtos.BookDTO;
import com.switchfully.digibookygroup3.api.dtos.BookWithSummaryDTO;
import com.switchfully.digibookygroup3.domain.book.Author;
import com.switchfully.digibookygroup3.domain.book.AuthorRepository;
import com.switchfully.digibookygroup3.domain.book.Book;
import com.switchfully.digibookygroup3.domain.book.BookRepository;
import com.switchfully.digibookygroup3.domain.user.User;
import com.switchfully.digibookygroup3.domain.user.UserRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;


import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private UserRepository userRepository;

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
                .get("/books/search?isbn=*543*")
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
                .get("/books/search?isbn=*543*")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().as(BookDTO[].class);

        assertEquals(0, filteredBooks.length);
    }

    @Test
    void deleteBook_givenAnInvalidUserAnExistingBook_whenDeleteBook_thenReturnsUnauthorized() {
        addBooksAndAuthors();
        String bookId = book.getId();
        RestAssured
                .given()
                .port(port)
                .delete("/books/" + bookId)
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void deleteBook_givenAnInvalidUserAnExistingBook_whenDeleteBook_thenReturnsForbidden() {
        addBooksAndAuthors();
        User admin = User.createAdmin("last", "first", "emailInvalid", "passwordInvalid");
        userRepository.addUser(admin);

        String bookId = book.getId();
        RestAssured
                .given()
                .auth()
                .preemptive()
                .basic(admin.getEmail(), admin.getPassword())
                .port(port)
                .delete("/books/" + bookId)
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void deleteBook_givenAValidUserAnExistingBook_whenDeleteBook_thenReturnsOK() {
        addBooksAndAuthors();
        User librarian = User.createLibrarian("last", "first", "email", "password");
        userRepository.addUser(librarian);

        String bookId = book.getId();
        RestAssured
                .given()
                .auth()
                .preemptive()
                .basic(librarian.getEmail(), librarian.getPassword())
                .port(port)
                .delete("/books/" + bookId)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deleteBook_givenAnExistingButDeletedBook_whenDeleteBook_thenThrowsBookNotFoundException() {
        addBooksAndAuthors();
        User librarian = User.createLibrarian("last", "first", "email", "password");
        userRepository.addUser(librarian);
        book.softDelete();
        String bookId = book.getId();
        RestAssured
                .given()
                .auth()
                .preemptive()
                .basic(librarian.getEmail(), librarian.getPassword())
                .port(port)
                .delete("/books/" + bookId)
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deleteBook_givenNonExistingBook_whenDeleteBook_thenThrowsBookNotFoundException() {
        User librarian = User.createLibrarian("last", "first", "email", "password");
        userRepository.addUser(librarian);

        RestAssured
                .given()
                .auth()
                .preemptive()
                .basic(librarian.getEmail(), librarian.getPassword())
                .port(port)
                .delete("/books/Deleted")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void restoreBook_givenAnExistingButDeletedBook_whenRestoreBook_thenReturnsOK() {
        addBooksAndAuthors();
        User librarian = User.createLibrarian("last", "first", "email", "password");
        userRepository.addUser(librarian);
        book.softDelete();

        String bookId = book.getId();
        RestAssured
                .given()
                .auth()
                .preemptive()
                .basic(librarian.getEmail(), librarian.getPassword())
                .port(port)
                .put("/books/restore/" + bookId)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void restoreBook_givenNonExistingBook_whenRestoreBook_thenThrowsBookNotFoundException() {
        User librarian = User.createLibrarian("last", "first", "email", "password");
        userRepository.addUser(librarian);

        RestAssured
                .given()
                .auth()
                .preemptive()
                .basic(librarian.getEmail(), librarian.getPassword())
                .port(port)
                .put("/books/restore/invalid")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void restoreBook_givenAnInvalidUserAnExistingBook_whenRestoreBook_thenReturnsUnauthorized() {
        addBooksAndAuthors();
        String bookId = book.getId();
        RestAssured
                .given()
                .port(port)
                .put("/books/restore/" + bookId)
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void restoreBook_givenAnInvalidUserAnExistingBook_whenRestoreBook_thenReturnsForbidden() {
        addBooksAndAuthors();
        User admin = User.createAdmin("last", "first", "emailTest", "passwordtest");
        userRepository.addUser(admin);

        String bookId = book.getId();
        RestAssured
                .given()
                .auth()
                .preemptive()
                .basic(admin.getEmail(), admin.getPassword())
                .port(port)
                .put("/books/restore/" + bookId)
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }
}