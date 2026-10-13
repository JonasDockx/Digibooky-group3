package com.switchfully.digibookygroup3.domain.user;

public class Librarian extends User{
    public Librarian(String lastname, String firstname, String email) {
        super(lastname, firstname, email, UserType.LIBRARIAN);
    }
}
