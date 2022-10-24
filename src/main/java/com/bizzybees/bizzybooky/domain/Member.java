package com.bizzybees.bizzybooky.domain;

import com.bizzybees.bizzybooky.security.Feature;
import com.bizzybees.bizzybooky.security.Role;

import java.util.UUID;

public class Member {
    private String memberId;
    private final String INSS;
    private final String password;
    private  Role role;
    private String lastname;
    private String firstname;
    private String email;
    private String streetName;
    private String streetNumber;
    private String postalCode;
    private String city;

    public Member( String INSS, String password, String lastname, String firstname, String email, String streetName, String streetNumber, String postalCode, String city) {
        this.memberId = UUID.randomUUID().toString();
        this.INSS = INSS;
        this.password = password;
        this.role = Role.MEMBER;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
        this.city = city;
    }

    public boolean doesPasswordMatch(String password) {
        return this.password.equals(password);
    }

    public boolean canHaveAccessTo(Feature feature) {
        return role.containsFeature(feature);
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

    public String getMemberId() {
        return memberId;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
