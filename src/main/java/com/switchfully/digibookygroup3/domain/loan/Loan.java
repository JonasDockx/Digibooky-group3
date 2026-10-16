package com.switchfully.digibookygroup3.domain.loan;

import java.time.LocalDate;
import java.util.UUID;

public class Loan {
    private static final int LOAN_PERIOD = 3;

    private String id;
    private String userId;
    private String bookId;
    private LocalDate dueDate;
    private boolean isActive;

    public Loan(String userId, String bookId) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.bookId = bookId;
        this.dueDate = LocalDate.now().plusWeeks(LOAN_PERIOD);
        this.isActive = true;
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

    public boolean isActive() {
        return isActive;
    }

    public void markAsInactive() {
        isActive = false;
    }
}
