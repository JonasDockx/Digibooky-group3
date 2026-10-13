package com.switchfully.digibookygroup3.domain.user;

import java.util.UUID;

public abstract class User {
    public String getId() {
        return id;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getEmail() {
        return email;
    }

    public UserType getUserType() {
        return userType;
    }

    private final String id;
    private final String lastname;
    private final String firstname;
    private String email;
    private UserType userType;

    public User(String lastname, String firstname, String email, UserType userType) {
        this.id = UUID.randomUUID().toString();
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.userType = userType;
    }
}
