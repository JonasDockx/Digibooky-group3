package com.switchfully.digibookygroup3.api;

import com.switchfully.digibookygroup3.api.dtos.CreateLoanDTO;
import com.switchfully.digibookygroup3.api.dtos.LoanDTO;
import com.switchfully.digibookygroup3.domain.book.Book;
import com.switchfully.digibookygroup3.domain.book.BookRepository;
import com.switchfully.digibookygroup3.domain.loan.Loan;
import com.switchfully.digibookygroup3.domain.loan.LoanRepository;
import com.switchfully.digibookygroup3.domain.user.Member;
import com.switchfully.digibookygroup3.domain.user.User;
import com.switchfully.digibookygroup3.domain.user.UserRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoanControllerIntegrationTest {

    private static final String PATH = "/loans";

    @LocalServerPort
    private int port;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    private Book availableBook;
    private Book unavailableBook;
    private User user;
    private Loan loan;


    private void addBooks() {
        availableBook = new Book("123456789", "Test", "id", "Test test test summary");
        bookRepository.save(availableBook);
        unavailableBook = new Book("223456789", "Test2", "id2", "Test test test summary2");
        unavailableBook.markAsUnavailable();
        bookRepository.save(unavailableBook);
    }

    private void addMember(){
        user = new Member(
                "member",
                "member",
                "member",
                "member@member.com",
                "member",
                "member",
                "member",
                "member",
                "pass");
        userRepository.addUser(user);
    }

    private void addLoan(){
        loan = new Loan(user.getId(), unavailableBook.getId());
    }

    @Test
    void givenRightCredentialsAndRequest_then201(){
        addBooks();
        addMember();
        CreateLoanDTO dto = new CreateLoanDTO(availableBook.getIsbn(), user.getId());

        LoanDTO loanDTO = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(dto)
                .auth()
                .preemptive()
                .basic(user.getEmail(), user.getPassword())
                .when()
                .port(port)
                .post(PATH)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(LoanDTO.class);

        assertThat(loanDTO).isNotNull();
        assertThat(loanDTO.bookIsbn().equals(availableBook.getIsbn()));
        assertThat(loanDTO.userId().equals(user.getId()));
        assertThat(loanDTO.returnDate().equals(LocalDate.now().plusWeeks(3)));
    }

    @Test
    void givenWrongCredentialsAndRequest_then403(){
        addBooks();
        addMember();
        CreateLoanDTO dto = new CreateLoanDTO(availableBook.getIsbn(), user.getId());

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(dto)
                .auth()
                .preemptive()
                .basic("admin@admin.com", "pass")
                .when()
                .port(port)
                .post(PATH)
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());

    }

    @Test
    void givenRightCredentialsAndWrongBookIsbn_then404(){
        addBooks();
        addMember();
        CreateLoanDTO dto = new CreateLoanDTO("nn", user.getId());

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(dto)
                .auth()
                .preemptive()
                .basic(user.getEmail(), user.getPassword())
                .when()
                .port(port)
                .post(PATH)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

    }

    @Test
    void givenNonExistentUser_then404(){
        addBooks();
        addMember();
        CreateLoanDTO dto = new CreateLoanDTO(availableBook.getIsbn(), "nn");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(dto)
                .auth()
                .preemptive()
                .basic(user.getEmail(), user.getPassword())
                .when()
                .port(port)
                .post(PATH)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void givenWrightCredentialAndUnavailableBook_then404(){
        addBooks();
        addMember();
        CreateLoanDTO dto = new CreateLoanDTO(unavailableBook.getIsbn(), user.getId());

        String response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(dto)
                .auth()
                .preemptive()
                .basic(user.getEmail(), user.getPassword())
                .when()
                .port(port)
                .post(PATH)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .extract()
                .asString();

        assertThat(response.equals("Book with isbn: " + unavailableBook.getIsbn() + " is not available"));
    }
}
