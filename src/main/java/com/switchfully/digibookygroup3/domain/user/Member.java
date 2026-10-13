package com.switchfully.digibookygroup3.domain.user;

import java.util.Objects;

public class Member extends User{
    private final String inss;
    private String streetName;
    private String streetNumber;
    private String postalCode;
    private String city;

    public Member(String inss,
                  String lastname,
                  String firstname,
                  String email,
                  String streetName,
                  String streetNumber,
                  String postalCode,
                  String city) {
        super(lastname, firstname, email, UserType.MEMBER);
        this.inss = normalizeInss(inss).trim();
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
        this.city = city;
    }

    public String getId() {
        return super.getId();
    }

    public String getInss() {
        return inss;
    }

    public String getLastname() {
        return super.getLastname();
    }

    public String getFirstname() {
        return super.getFirstname();
    }

    public String getEmail() {
        return super.getEmail();
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

    private static String normalizeInss(String inss) {
        Objects.requireNonNull(inss, "INSS is required");
        return inss.replaceAll("\\D", "");
    }
}
