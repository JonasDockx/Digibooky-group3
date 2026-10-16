package com.switchfully.digibookygroup3.domain.loan;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class Loan {
    private static final int LOAN_PERIOD = 3;

    private final String id;
    private final String userId;
    private final String bookId;
    private boolean isActive;
    private final LocalDate loanDate;
    private final LocalDate dueDate;

    public Loan(String userId, String bookId) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.bookId = bookId;
        this.dueDate = LocalDate.now().plusWeeks(LOAN_PERIOD);
        this.isActive = true;
        this.loanDate = LocalDate.now();
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

    public void markAsInactive() {
        isActive = false;
    }

    public boolean isOverdue() {
        return LocalDate.now().isAfter(getDueDate());
    }

    public long getOverdueDays(){
        return ChronoUnit.DAYS.between(getDueDate(), LocalDate.now());
    }
}
