package com.bizzybees.bizzybooky.domain;

import java.util.UUID;

public class Member {
    private final String INSS;
    private String lastname;
    private String firstname;
    private String email;
    private String streetName;
    private String streetNumber;
    private String postalCode;
    private String City;

    public Member(String lastname, String firstname, String email, String streetName, String streetNumber, String postalCode, String city) {
        this.INSS = UUID.randomUUID().toString();
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
        City = city;
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
        return City;
    }
}
