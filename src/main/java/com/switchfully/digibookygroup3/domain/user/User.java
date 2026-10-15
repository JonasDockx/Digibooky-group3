package com.switchfully.digibookygroup3.domain.user;

import java.util.UUID;

public class User {
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
    private final String email;
    private final UserType userType;
    private final String password;

    protected User(String lastname, String firstname, String email, UserType userType, String password) {
        this.id = UUID.randomUUID().toString();
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.userType = userType;
        this.password = password;
    }

    public static User createAdmin(String lastname, String firstname, String email, String password) {
        return new User(lastname, firstname, email, UserType.ADMIN, password);
    }

    public static User createLibrarian(String lastname, String firstname, String email, String password) {
        return new User(lastname, firstname, email, UserType.LIBRARIAN, password);
    }

    public String getPassword() {
        return password;
    }

    public boolean canHaveAccessTo(Feature feature) {
        return userType.containsFeature(feature);
    }
}
