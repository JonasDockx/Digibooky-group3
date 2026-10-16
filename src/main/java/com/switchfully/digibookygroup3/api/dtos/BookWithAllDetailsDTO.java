package com.switchfully.digibookygroup3.api.dtos;


import com.switchfully.digibookygroup3.domain.user.Member;

public class BookWithAllDetailsDTO extends BookDTO {
    private final String summary;
    private final boolean isLoanedOut = true;
    private final String memberId;

    public BookWithAllDetailsDTO(String id, String title, String isbn, AuthorDTO authorDTO, String summary, String memberId) {
        super(id, title, isbn, authorDTO);
        this.summary = summary;
        this.memberId = memberId;
    }

    public String getSummary() {
        return summary;
    }

}
