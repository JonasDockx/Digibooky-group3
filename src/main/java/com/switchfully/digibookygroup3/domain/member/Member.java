package com.switchfully.digibookygroup3.domain.member;

public class Member {
    private final String id;
    private final String INSS;
    private final String lastname;
    private final String firstname;
    private String email;
    private String streetName;
    private String streetNumber;
    private String postalCode;
    private String city;
    private MemberType memberType;

    public Member(String id, String INSS,
                  String lastname,
                  String firstname,
                  String email,
                  String streetName,
                  String streetNumber,
                  String postalCode,
                  String city,
                  MemberType memberType) {
        this.id = id;
        this.INSS = INSS;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.memberType = memberType;
    }
}
