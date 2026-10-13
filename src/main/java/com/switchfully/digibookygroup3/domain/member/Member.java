package com.switchfully.digibookygroup3.domain.member;

import java.util.UUID;

public class Member {
    private final String id;
    private final String inss;
    private final String lastname;
    private final String firstname;
    private String email;
    private String streetName;
    private String streetNumber;
    private String postalCode;
    private String city;
    private MemberType memberType;

    public Member(String inss,
                  String lastname,
                  String firstname,
                  String email,
                  String streetName,
                  String streetNumber,
                  String postalCode,
                  String city) {
        this.id = UUID.randomUUID().toString();
        this.inss = inss;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.memberType = MemberType.MEMBER;
    }

    public String getId() {
        return id;
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

    public MemberType getMemberType() {
        return memberType;
    }
}
