package com.switchfully.digibookygroup3.domain.loan;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.UUID;

public class Loan {
    private static final int LOAN_PERIOD = 3;

    private String id;
    private String userId;
    private String bookId;
    private LocalDate loanDate;
    private LocalDate dueDate;

    public Loan(String userId, String bookId) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.bookId = bookId;
        this.loanDate = LocalDate.now();
        this.dueDate = loanDate.plusWeeks(LOAN_PERIOD);
    }

    public Loan(String userId, String bookId, LocalDate loanDate) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.bookId = bookId;
        this.loanDate = loanDate;
        this.dueDate = loanDate.plusWeeks(LOAN_PERIOD);
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getBookId() {
        return bookId;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isOverdue() {
        return LocalDate.now().isAfter(getDueDate());
    }
}
