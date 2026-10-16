package com.switchfully.digibookygroup3.domain.loan;

import java.time.LocalDate;
import java.util.UUID;

public class Loan {
    private String id;
    private String userId;
    private String bookId;
    private LocalDate returnDate;

    public Loan(String userId, String bookId, LocalDate returnDate) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.bookId = bookId;
        this.returnDate = returnDate;
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

    public LocalDate getReturnDate() {
        return returnDate;
    }
}
