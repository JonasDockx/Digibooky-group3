package com.switchfully.digibookygroup3.api;

import com.switchfully.digibookygroup3.api.dtos.BookDTO;
import com.switchfully.digibookygroup3.api.dtos.CreateLoanDTO;
import com.switchfully.digibookygroup3.api.dtos.LoanDTO;
import com.switchfully.digibookygroup3.domain.book.BookRepository;
import com.switchfully.digibookygroup3.domain.user.Feature;
import com.switchfully.digibookygroup3.service.BookService;
import com.switchfully.digibookygroup3.service.LoanService;
import com.switchfully.digibookygroup3.service.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/loans")
public class LoanController {
    private static final String APPLICATION_JSON = "application/json";
    private final LoanService loanService;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private SecurityService securityService;

    public LoanController(LoanService loanService, SecurityService securityService, BookRepository bookRepository, BookService bookService) {

        this.loanService = loanService;
        this.securityService = securityService;
        this.bookRepository = bookRepository;
        this.bookService = bookService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public LoanDTO lendABook(@RequestBody CreateLoanDTO createLoanDTO) {
        return loanService.lendABook(createLoanDTO.bookId(), createLoanDTO.userId());
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public List<BookDTO> viewLentBooks(@RequestHeader(value = "Authorization", required = false) String authorization, @PathVariable("id") String id) {
        securityService.validateAuthorization(authorization, Feature.VIEW_LENT_BOOKS);
        return loanService.getLentBooksByUser(id);
    }
}
