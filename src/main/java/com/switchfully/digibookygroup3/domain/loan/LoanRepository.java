package com.switchfully.digibookygroup3.domain.loan;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    public Optional<Loan> getLoanById(String id) {
        return Optional.ofNullable(loans.get(id));
    }
}
