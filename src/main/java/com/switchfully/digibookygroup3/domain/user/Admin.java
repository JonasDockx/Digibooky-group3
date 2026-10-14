package com.switchfully.digibookygroup3.domain.user;

public class Admin extends User {
    public Admin(String lastname, String firstname, String email, String password) {
        super(lastname, firstname, email, UserType.ADMIN, password);
    }
}
