package com.switchfully.digibookygroup3.api;

import com.switchfully.digibookygroup3.api.dtos.CreateLoanDTO;
import com.switchfully.digibookygroup3.api.dtos.LoanDTO;
import com.switchfully.digibookygroup3.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/loans")
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public LoanDTO lendABook(@RequestBody CreateLoanDTO createLoanDTO) {
        return loanService.lendABook(createLoanDTO.bookId(), createLoanDTO.userId());
    }
}
