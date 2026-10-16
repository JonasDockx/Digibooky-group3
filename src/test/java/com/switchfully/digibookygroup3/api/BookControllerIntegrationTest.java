package com.switchfully.digibookygroup3.api;

import com.switchfully.digibookygroup3.api.dtos.AuthorDTO;
import com.switchfully.digibookygroup3.api.dtos.BookDTO;
import com.switchfully.digibookygroup3.api.dtos.BookWithSummaryDTO;
import com.switchfully.digibookygroup3.api.dtos.UpdateBookDTO;
import com.switchfully.digibookygroup3.api.dtos.CreateBookDTO;
import com.switchfully.digibookygroup3.api.dtos.CreateLibrarianDTO;
import com.switchfully.digibookygroup3.domain.book.Author;
import com.switchfully.digibookygroup3.domain.book.AuthorRepository;
import com.switchfully.digibookygroup3.domain.book.Book;
import com.switchfully.digibookygroup3.domain.book.BookRepository;
import com.switchfully.digibookygroup3.domain.user.User;
import com.switchfully.digibookygroup3.domain.user.UserRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
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
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
    }

    private void addBooksAndAuthors() {
        Author author = new Author("Jonas", "Dockx");
        book = new Book("123456789", "Test", author.getId(), "Test test test summary");

        authorRepository.save(author);
        bookRepository.save(book);
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
        authorRepository.save(author);
        bookRepository.save(new Book("987654321", "Test", author.getId(), "Test summary"));

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
    void updateBook_givenAnExistingBookWithValidAuthor_whenUpdateBook_thenReturnsTheUpdatedBook() {
        addBooksAndAuthors();
        Book book1 = bookRepository.getAllBooks().get(0);
        AuthorDTO authorDTO = new AuthorDTO(book1.getAuthorId(), "Dockx", "Jonas");

        UpdateBookDTO updateBookDTO = new UpdateBookDTO("test", "test", "test", authorDTO);

        BookWithSummaryDTO result = RestAssured.given()
                .body(updateBookDTO)
                .contentType(JSON)
                .port(port)
                .put("/books/" + book1.getId())
                .then().statusCode(HttpStatus.OK.value())
                .extract().as(BookWithSummaryDTO.class);

        assertThat(result.getAuthorDTO()).isEqualTo(authorDTO);
        assertThat(result.getTitle()).isEqualTo(updateBookDTO.title());
        assertThat(result.getIsbn()).isEqualTo(updateBookDTO.isbn());
        assertThat(result.getSummary()).isEqualTo(updateBookDTO.summary());

    }


    @Test
    void updateBook_givenAnExistingBookWithInvalidAuthor_whenUpdateBook_thenThrowsAuthorNotFoundException() {
        UpdateBookDTO updateBookDTO = new UpdateBookDTO("a", "1", "ab", new AuthorDTO("1", "firstname", "lastname"));

        String result = RestAssured.given()
                .body(updateBookDTO)
                .contentType(JSON)
                .port(port)
                .put("/books/999")
                .then().statusCode(HttpStatus.NOT_FOUND.value())
                .extract().asString();

        assertEquals("Author not found with id 1", result);
    }


    @Test
    void updateBook_givenANonExistingBook_whenUpdateBook_thenThrowsBookNotFoundException() {
        addBooksAndAuthors();
        Book book1 = bookRepository.getAllBooks().get(0);
        AuthorDTO authorDTO = new AuthorDTO(book1.getAuthorId(), "Jonas", "Dockx");

        UpdateBookDTO updateBookDTO = new UpdateBookDTO("a", "1", "ab", authorDTO);

        String result = RestAssured.given()
                .body(updateBookDTO)
                .contentType(JSON)
                .port(port)
                .put("/books/999")
                .then().statusCode(HttpStatus.NOT_FOUND.value())
                .extract().asString();

        assertEquals("Book not found with id 999", result);
    }

    @Test
    void registerBook_givenRightCredentialsAndInput_thenReturn201(){
        addBooksAndAuthors();

       userRepository.addUser(User.createLibrarian(
                "librarian",
                "librarian",
                "librarian@librarian.lib",
                "pass"
        ));


        CreateBookDTO createBookDTO = new CreateBookDTO(
                "654654",
                "test",
                "Dockx",
                "Jonas",
                "test"
        );

        BookDTO bookDTO = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(createBookDTO)
                .auth()
                .preemptive()
                .basic("librarian@librarian.lib", "pass")
                .when()
                .post("http://localhost:" + port + "/books")
                .then()
                .statusCode(201)
                .extract()
                .as(BookDTO.class);

        assertEquals("654654", bookDTO.getIsbn());
        assertEquals("Dockx", bookDTO.getAuthorDTO().lastname());
    }

    @Test
    void registerBook_givenWrongCredentialsAndRightInput_thenReturn403(){
        addBooksAndAuthors();

        CreateBookDTO createBookDTO = new CreateBookDTO(
                "654654",
                "test",
                "Dockx",
                "Jonas",
                "test"
        );

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(createBookDTO)
                .auth()
                .preemptive()
                .basic("admin@admin.com", "pass")
                .when()
                .post("http://localhost:" + port + "/books")
                .then()
                .statusCode(403);
    }
}