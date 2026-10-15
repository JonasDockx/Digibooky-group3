package com.switchfully.digibookygroup3.api.dtos;

import com.switchfully.digibookygroup3.domain.user.UserType;

public class LibrarianDTO {
    private final String id;
    private final String lastname;
    private final String firstname;
    private final String email;
    private final UserType userType;

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public UserType getUserType() {
        return userType;
    }

    public LibrarianDTO(String id, String lastname, String firstname, String email, UserType userType) {
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.userType = userType;
    }
}
