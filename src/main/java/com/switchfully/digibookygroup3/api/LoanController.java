package com.switchfully.digibookygroup3.api;

import com.switchfully.digibookygroup3.api.dtos.CreateLoanDTO;
import com.switchfully.digibookygroup3.api.dtos.LoanDTO;
import com.switchfully.digibookygroup3.domain.user.Feature;
import com.switchfully.digibookygroup3.service.LoanService;
import com.switchfully.digibookygroup3.service.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/loans")
public class LoanController {
    private final LoanService loanService;
    private final SecurityService securityService;

    public LoanController(LoanService loanService, SecurityService securityService) {
        this.loanService = loanService;
        this.securityService = securityService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoanDTO lendABook(@RequestHeader(value = "Authorization", required = false) String authorization, @RequestBody CreateLoanDTO createLoanDTO) {
        securityService.validateAuthorization(authorization, Feature.LEND_BOOK);
        return loanService.lendABook(createLoanDTO.bookIsbn(), createLoanDTO.userId());
    }
}
