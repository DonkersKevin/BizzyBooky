package com.bizzybees.bizzybooky.domain.dto.memberDtos;


import com.bizzybees.bizzybooky.security.Role;

import java.util.Objects;

public class ReturnMemberDto {
    private Role role;

    private String lastname;
    private String firstname;
    private String email;
    private String streetName;
    private String streetNumber;
    private String postalCode;
    private String City;



    public ReturnMemberDto(Role role, String lastname, String firstname, String email, String streetName, String streetNumber, String postalCode, String city) {
        this.role = role;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReturnMemberDto that = (ReturnMemberDto) o;
        return getRole() == that.getRole() && Objects.equals(getLastname(), that.getLastname()) && Objects.equals(getFirstname(), that.getFirstname()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getStreetName(), that.getStreetName()) && Objects.equals(getStreetNumber(), that.getStreetNumber()) && Objects.equals(getPostalCode(), that.getPostalCode()) && Objects.equals(getCity(), that.getCity());
    }


}

