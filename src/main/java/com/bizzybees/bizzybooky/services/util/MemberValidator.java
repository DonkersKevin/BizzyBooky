package com.bizzybees.bizzybooky.services.util;

import com.bizzybees.bizzybooky.domain.dto.memberDtos.NewMemberDto;

public class MemberValidator {
    public void isValidEmail(String emailAddress) {

        if (!emailAddress.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
            throw new IllegalArgumentException("Email address is not valid");
        }

    }

    //ToDo add unit test for lastname and inss
    public void checkRequiredFields(NewMemberDto newMemberDto) {
        if (newMemberDto.getEmail() == null || newMemberDto.getEmail().equals("")) {
            throw new IllegalArgumentException("Provide an Email address please!");
        }
        if (newMemberDto.getCity() == null || newMemberDto.getCity().equals("")) {
            throw new IllegalArgumentException("Provide a City please!");
        }
        if (newMemberDto.getLastname() == null || newMemberDto.getLastname().equals("")) {
            throw new IllegalArgumentException("Provide a lastname please!");
        }
        if (newMemberDto.getINSS() == null || newMemberDto.getINSS().equals("")) {
            throw new IllegalArgumentException("Provide an INSS number please!");
        }
    }
}
