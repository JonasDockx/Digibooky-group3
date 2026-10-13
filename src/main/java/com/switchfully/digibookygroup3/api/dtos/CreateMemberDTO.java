package com.switchfully.digibookygroup3.api.dtos;

import com.switchfully.digibookygroup3.domain.member.MemberType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CreateMemberDTO {
    @NotBlank
    private final String INSS;
    @NotBlank
    private final String lastname;
    private final String firstname;
    @NotBlank
    @Email
    private String email;
    private String streetName;
    private String streetNumber;
    private String postalCode;
    @NotBlank
    private String city;

    public CreateMemberDTO(String INSS,
                           String lastname,
                           String firstname,
                           String email,
                           String streetName,
                           String streetNumber,
                           String postalCode,
                           String city) {
        this.INSS = INSS;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
        this.city = city;
    }

    public String getINSS() {
        return INSS;
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

}
