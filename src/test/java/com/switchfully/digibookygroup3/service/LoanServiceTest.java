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
import com.switchfully.digibookygroup3.mapper.AuthorMapper;
import com.switchfully.digibookygroup3.mapper.BookMapper;
import com.switchfully.digibookygroup3.mapper.LoanMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class LoanServiceTest {

    private BookRepository bookRepository;
    private LoanRepository loanRepository;
    private AuthorRepository authorRepository;
    private UserRepository userRepository;
    private LoanService loanService;

    @BeforeEach
    void setUp() {
        bookRepository = new BookRepository();
        loanRepository = new LoanRepository();
        authorRepository = new AuthorRepository();
        userRepository = new UserRepository();
        loanService = new LoanService(
                bookRepository,
                userRepository,
                loanRepository,
                new LoanMapper(),
                new BookMapper(),
                authorRepository,
                new AuthorMapper()
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

        Assertions.assertThat(result).hasSize(1);
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

        Assertions.assertThat(result).hasSize(2);
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

        Assertions.assertThat(result).hasSize(1);
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

        Assertions.assertThat(result).isEmpty();
    }
}