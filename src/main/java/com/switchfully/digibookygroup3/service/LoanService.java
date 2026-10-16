package com.switchfully.digibookygroup3.service;

import com.switchfully.digibookygroup3.api.dtos.LoanDTO;
import com.switchfully.digibookygroup3.domain.book.Book;
import com.switchfully.digibookygroup3.domain.book.BookRepository;
import com.switchfully.digibookygroup3.domain.loan.Loan;
import com.switchfully.digibookygroup3.domain.loan.LoanRepository;
import com.switchfully.digibookygroup3.domain.user.User;
import com.switchfully.digibookygroup3.domain.user.UserRepository;
import com.switchfully.digibookygroup3.exception.BookNotFoundByIsbnException;
import com.switchfully.digibookygroup3.exception.BookNotFoundException;
import com.switchfully.digibookygroup3.exception.BookUnavailableException;
import com.switchfully.digibookygroup3.exception.UserNotFoundException;
import com.switchfully.digibookygroup3.mapper.LoanMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LoanService {
    private static final int LOAD_PERIOD = 3;

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;

    public LoanService(BookRepository bookRepository, UserRepository userRepository, LoanRepository loanRepository, LoanMapper loanMapper) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
    }

    public LoanDTO lendABook(String bookIsbn, String userId) {
            doesUserExist(userId);
            Book book = getBookByIsbn(bookIsbn);

            if (!book.isAvailable()) {
                throw new BookUnavailableException(bookIsbn);
            }

            Loan loan = loanRepository.save(new Loan(userId, book.getId(), getReturnDate()));
            book.markAsUnavailable();
            return loanMapper.mapToDTO(loan, book.getIsbn());
    }

    private Book getBookByIsbn(String bookIsbn) {
        return bookRepository.getBookByIsbn(bookIsbn)
                .orElseThrow(() -> new BookNotFoundByIsbnException(bookIsbn));
    }

    private void doesUserExist(String userId) {
        userRepository.getById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    private LocalDate getReturnDate() {
        return LocalDate.now().plusWeeks(LOAD_PERIOD);
    }

}
