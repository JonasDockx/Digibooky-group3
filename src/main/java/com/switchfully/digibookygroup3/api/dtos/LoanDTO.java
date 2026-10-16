package com.switchfully.digibookygroup3.api.dtos;

import java.time.LocalDate;

public record LoanDTO(String id, String userId, String bookIsbn, LocalDate returnDate) {
}
