package com.switchfully.digibookygroup3.service;

import com.switchfully.digibookygroup3.api.dtos.BookDTO;
import com.switchfully.digibookygroup3.api.dtos.LoanDTO;
import com.switchfully.digibookygroup3.domain.book.AuthorRepository;
import com.switchfully.digibookygroup3.domain.book.Book;
import com.switchfully.digibookygroup3.domain.book.BookRepository;
import com.switchfully.digibookygroup3.domain.loan.Loan;
import com.switchfully.digibookygroup3.domain.loan.LoanRepository;
import com.switchfully.digibookygroup3.domain.user.UserRepository;
import com.switchfully.digibookygroup3.exception.*;
import com.switchfully.digibookygroup3.mapper.AuthorMapper;
import com.switchfully.digibookygroup3.mapper.BookMapper;
import com.switchfully.digibookygroup3.mapper.LoanMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LoanService {


    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final BookMapper bookMapper;
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public LoanService(BookRepository bookRepository, UserRepository userRepository, LoanRepository loanRepository,
                       LoanMapper loanMapper, BookMapper bookMapper, AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
        this.bookMapper = bookMapper;
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    private List<BookDTO> convertLoanListToBookDTOList(List<Loan> loanList) {
        return loanList.stream()
                .map(loan -> getBookById(loan.getBookId()))
                .map(book -> bookMapper.toDto(book, authorMapper.toDto(authorRepository.getById(book.getAuthorId())
                        .orElseThrow(() -> new AuthorNotFoundException(book.getAuthorId())))))
                .toList();
    }

    public List<BookDTO> getLentBooksByUser(String id) {
        List<Loan> loans = loanRepository.getLoans();
        List<Loan> filteredLoans = loans.stream()
                .filter(loan -> loan.getUserId().equals(id))
                .toList();

        return convertLoanListToBookDTOList(filteredLoans);
    }

    public List<BookDTO> getAllOverdueBooks() {
        List<Loan> loans = loanRepository.getLoans();

        List<Loan> loanList = loans.stream()
                .filter(Loan::isOverdue)
                .toList();

        return convertLoanListToBookDTOList(loanList);
    }

    public LoanDTO lendABook(String bookIsbn, String userId) {
        doesUserExist(userId);
        Book book = getBookByIsbn(bookIsbn);

        if (!book.isAvailable()) {
            throw new BookUnavailableException(bookIsbn);
        }

        Loan loan = loanRepository.save(new Loan(userId, book.getId()));
        book.markAsUnavailable();
        return loanMapper.mapToDTO(loan, book.getIsbn());
    }

    private Book getBookByIsbn(String bookIsbn) {
        return bookRepository.getBookByIsbn(bookIsbn)
                .orElseThrow(() -> new BookNotFoundByIsbnException(bookIsbn));
    }

    private Book getBookById(String bookId) {
        return bookRepository.getBydId(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }

    private void doesUserExist(String userId) {
        userRepository.getById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    public String returnBook(String id) {
        String response = "Your book has been returned";
        Loan loan = loanRepository.getLoanById(id)
                        .orElseThrow(() -> new LoanNotFoundException(id));
        Book book = getBookById(loan.getBookId());

        loan.markAsInactive();
        book.markAsAvailable();

        if(loan.isOverdue()){
            response += " with overdue of " + loan.getOverdueDays() + " days";
        }
        return response;
    }
}
