package com.switchfully.digibookygroup3.service;

import com.switchfully.digibookygroup3.api.dtos.LoanDTO;
import com.switchfully.digibookygroup3.domain.book.Book;
import com.switchfully.digibookygroup3.domain.book.BookRepository;
import com.switchfully.digibookygroup3.domain.loan.Loan;
import com.switchfully.digibookygroup3.domain.loan.LoanRepository;
import com.switchfully.digibookygroup3.domain.user.User;
import com.switchfully.digibookygroup3.domain.user.UserRepository;
import com.switchfully.digibookygroup3.exception.BookNotFoundException;
import com.switchfully.digibookygroup3.exception.BookUnavailableException;
import com.switchfully.digibookygroup3.exception.UserNotFoundException;
import com.switchfully.digibookygroup3.mapper.LoanMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LoanService {


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

    public LoanDTO lendABook(String bookId, String userId) {
        doesUserExist(userId);
        Book book = getBookById(bookId);

        if (!book.isAvailable()) {
            throw new BookUnavailableException(bookId);
        }

        Loan loan = loanRepository.save(new Loan(userId, bookId));
        book.markAsUnavailable();
        return loanMapper.mapToDTO(loan, book.getIsbn());
    }

    private Book getBookById(String bookId) {
        return bookRepository.getBydId(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }

    private void doesUserExist(String userId) {
        userRepository.getById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

}
