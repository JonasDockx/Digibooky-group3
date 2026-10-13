package com.switchfully.digibookygroup3.domain.book;

import java.util.UUID;

public class Author {
    private final String id;
    private final String firstname;
    private final String lastname;

    public Author(String firstname, String lastname) {
        this.id = UUID.randomUUID().toString();
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}
