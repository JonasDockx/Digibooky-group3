package com.switchfully.digibookygroup3.domain.loan;

import org.springframework.stereotype.Repository;

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
}
