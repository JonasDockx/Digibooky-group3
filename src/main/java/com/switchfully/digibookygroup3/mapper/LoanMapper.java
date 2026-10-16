package com.switchfully.digibookygroup3.mapper;

import com.switchfully.digibookygroup3.api.dtos.LoanDTO;
import com.switchfully.digibookygroup3.domain.loan.Loan;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {

    public LoanDTO mapToDTO(Loan loan, String bookIsbn) {
        if (loan == null) {
            return null;
        }
        return new LoanDTO(loan.getId(), loan.getUserId(), bookIsbn, loan.getDueDate());
    }
}
