package com.switchfully.digibookygroup3.service;

import com.switchfully.digibookygroup3.api.dtos.BookDTO;
import com.switchfully.digibookygroup3.domain.book.Author;
import com.switchfully.digibookygroup3.domain.book.AuthorRepository;
import com.switchfully.digibookygroup3.domain.book.Book;
import com.switchfully.digibookygroup3.domain.book.BookRepository;
import com.switchfully.digibookygroup3.domain.loan.Loan;
import com.switchfully.digibookygroup3.domain.loan.LoanRepository;
import com.switchfully.digibookygroup3.domain.user.Member;
import com.switchfully.digibookygroup3.domain.user.User;
import com.switchfully.digibookygroup3.domain.user.UserRepository;
import com.switchfully.digibookygroup3.domain.user.UserType;
import com.switchfully.digibookygroup3.mapper.AuthorMapper;
import com.switchfully.digibookygroup3.mapper.BookMapper;
import com.switchfully.digibookygroup3.mapper.LoanMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoanServiceTest {

    private BookRepository bookRepository;
    private LoanRepository loanRepository;
    private AuthorRepository authorRepository;
    private UserRepository userRepository;
    private LoanMapper loanMapper;
    private BookMapper bookMapper;
    private AuthorMapper authorMapper;
    private LoanService loanService;
    private List<Author> authors;
    private List<Book> books;

    @BeforeEach
    void setUp() {
        bookRepository = new BookRepository();
        loanRepository = new LoanRepository();
        authorRepository = new AuthorRepository();
        userRepository = new UserRepository();
        loanMapper = new LoanMapper();
        bookMapper = new BookMapper(authorRepository);
        authorMapper = new AuthorMapper();
        loanService = new LoanService(
                bookRepository,
                userRepository,
                loanRepository,
                loanMapper,
                bookMapper,
                authorRepository,
                authorMapper
        );
    }

    @Test
    void givenOneBookBeingLoanedByAUser_thenGetLentBooksByUserShouldReturnAListOfOneBook() {
        User user = new Member("Dockx",
                "Jonas",
                "Test@test.com",
                "Test",
                "Test",
                "123",
                "4567",
                "Brussels",
                "password");
        String userId = user.getId();

        userRepository.addUser(user);
        Author author = new Author("Matthieu", "Martens");
        authorRepository.addAuthor(author);
        String authorId = author.getId();

        Book book = new Book("123456789", "BookTitle", authorId, "Summary");
        String bookID = book.getId();

        bookRepository.save(book);

        Loan loan = new Loan(userId, bookID);

        loanRepository.save(loan);

        List<BookDTO> result = loanService.getLentBooksByUser(userId);

        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void givenTwoBooksBeingLoanedByAUser_thenGetLentBooksByUserShouldReturnAListOfTwoBooks() {
        User user = new Member("Dockx",
                "Jonas",
                "Test@test.com",
                "Test",
                "Test",
                "123",
                "4567",
                "Brussels",
                "password");
        String userId = user.getId();

        userRepository.addUser(user);
        Author author = new Author("Matthieu", "Martens");
        authorRepository.addAuthor(author);
        String authorId = author.getId();

        Book book = new Book("123456789", "BookTitle", authorId, "Summary");
        String bookID = book.getId();

        bookRepository.save(book);

        Book book2 = new Book("123456789", "BookTitle2", authorId, "Summary");
        String book2ID = book2.getId();

        bookRepository.save(book2);

        Loan loan = new Loan(userId, bookID);
        Loan loan2 = new Loan(userId, book2ID);

        loanRepository.save(loan);
        loanRepository.save(loan2);

        List<BookDTO> result = loanService.getLentBooksByUser(userId);

        Assertions.assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void requestingAllOverdueBooksMethod_ReturnsAllBooksThatAreOverdue() {
        User user = new Member("Dockx",
                "Jonas",
                "Test@test.com",
                "Test",
                "Test",
                "123",
                "4567",
                "Brussels",
                "password");
        String userId = user.getId();

        userRepository.addUser(user);
        Author author = new Author("Matthieu", "Martens");
        authorRepository.addAuthor(author);
        String authorId = author.getId();

        Book book = new Book("123456789", "BookTitle", authorId, "Summary");
        String bookID = book.getId();

        bookRepository.save(book);

        Book book2 = new Book("123456789", "BookTitle2", authorId, "Summary");
        String book2ID = book2.getId();

        bookRepository.save(book2);

        Loan loan = new Loan(userId, bookID, LocalDate.parse("2026-09-18"));
        Loan loan2 = new Loan(userId, book2ID);

        loanRepository.save(loan);
        loanRepository.save(loan2);

        List<BookDTO> result = loanService.getAllOverdueBooks();

        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void requestingAllOverdueBooksMethod_ReturnsNoBooks_IfNoneAreOverdue() {
        User user = new Member("Dockx",
                "Jonas",
                "Test@test.com",
                "Test",
                "Test",
                "123",
                "4567",
                "Brussels",
                "password");
        String userId = user.getId();

        userRepository.addUser(user);
        Author author = new Author("Matthieu", "Martens");
        authorRepository.addAuthor(author);
        String authorId = author.getId();

        Book book = new Book("123456789", "BookTitle", authorId, "Summary");
        String bookID = book.getId();

        bookRepository.save(book);

        Book book2 = new Book("123456789", "BookTitle2", authorId, "Summary");
        String book2ID = book2.getId();

        bookRepository.save(book2);

        Loan loan = new Loan(userId, bookID);
        Loan loan2 = new Loan(userId, book2ID);

        loanRepository.save(loan);
        loanRepository.save(loan2);

        List<BookDTO> result = loanService.getAllOverdueBooks();

        Assertions.assertThat(result.size()).isEqualTo(0);
    }
}