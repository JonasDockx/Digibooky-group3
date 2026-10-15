package com.switchfully.digibookygroup3.api.dtos;

public class CreateLibrarianDTO {
    private final String lastname;
    private final String firstname;
    private final String email;
    private final String password;

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public CreateLibrarianDTO(String lastname, String firstname, String email, String password) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.password = password;
    }
}
