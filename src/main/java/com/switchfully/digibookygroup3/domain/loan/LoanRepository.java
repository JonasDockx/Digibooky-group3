package com.switchfully.digibookygroup3.domain.loan;

import com.switchfully.digibookygroup3.exception.BookNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class LoanRepository {
    private final Map<String, Loan> loans;

    public LoanRepository() {
        this.loans = new ConcurrentHashMap<>();
    }

    public Loan save(Loan loan) {
        loans.put(loan.getId(), loan);
        return loan;
    }

    public List<Loan> getLoans() {
        return new ArrayList<>(loans.values());
    }

    public String getUserIdByBookId(String bookId) {
        return loans.values().stream()
                .filter(loan -> loan.getBookId().equals(bookId))
                .map(Loan::getUserId)
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }
}
