package com.bizzybees.bizzybooky.domain.memberdtos;

import com.bizzybees.bizzybooky.security.Role;

public class NewMemberDto {
    private Role role;
    private String password;
    private String INSS;
    private String lastname;
    private String firstname;
    private String email;
    private String streetName;
    private String streetNumber;
    private String postalCode;
    private String City;

    public String getINSS() {
        return INSS;
    }

    public NewMemberDto(Role role, String password, String INSS, String lastname, String firstname, String email, String streetName, String streetNumber, String postalCode, String city) {
        this.role = role;
        this.password = password;
        this.INSS = INSS;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
        City = city;
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

    public Role getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }
}
