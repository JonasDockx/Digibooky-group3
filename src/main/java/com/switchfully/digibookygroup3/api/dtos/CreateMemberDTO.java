package com.switchfully.digibookygroup3.api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CreateMemberDTO {
    @NotBlank
    private final String inss;
    @NotBlank
    private final String lastname;
    private final String firstname;
    @NotBlank
    @Email(regexp = "^[A-Za-z0-9_%+-]+(?:\\.[A-Za-z0-9_%+-]+)*@(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z]{2,}$",
            message = "Email must be of the form name@domain.tld")
    private String email;
    private String streetName;
    private String streetNumber;
    private String postalCode;
    @NotBlank
    private String city;
    @NotBlank
    private final String password;

    public CreateMemberDTO(String inss,
                           String lastname,
                           String firstname,
                           String email,
                           String streetName,
                           String streetNumber,
                           String postalCode,
                           String city,
                           String password) {
        this.inss = inss;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.password = password;
    }

    public String getInss() {
        return inss;
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

    public String getStreetName() {
        return streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public String getPassword() { return password; }
}
