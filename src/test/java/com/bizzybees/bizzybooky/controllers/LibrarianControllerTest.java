package com.bizzybees.bizzybooky.controllers;

import com.bizzybees.bizzybooky.repositories.MemberRepository;
import com.bizzybees.bizzybooky.security.Role;
import com.bizzybees.bizzybooky.security.exception.UnknownUserException;
import com.bizzybees.bizzybooky.security.exception.WrongPasswordException;
import com.bizzybees.bizzybooky.services.memberdtos.NewMemberDto;
import com.bizzybees.bizzybooky.services.memberdtos.ReturnMemberDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class LibrarianControllerTest {
    @Autowired
    private LibrarianController librarianController;

    @Autowired
    private MemberRepository memberRepository;
    @Test
    void addNewLibrarianToRepositoryIsSuccessful() {
        //given
        NewMemberDto newMemberDto = new NewMemberDto(Role.MEMBER, "Squarepants", "Patrick", "Patrick@hotmail.com"
                , "randomstreet"
                , "Patrrick@hotmail.com", "1", "Bikini Bottom", "", "fefe");

        //when

        ReturnMemberDto returnMemberDto1 = librarianController.addLibrarian("Basic MjpTcXVhcmVwYW50cw==",newMemberDto);
        //then
        Assertions.assertTrue(memberRepository.memberDatabase.containsKey(returnMemberDto1.getINSS()));
    }

    @Test
    void addNewLibrarianWithWrongPassword_GivesUnknownUserException() {
        //given
        NewMemberDto newMemberDto = new NewMemberDto(Role.MEMBER, "Squarepants", "Patrick", "Patrick@hotmail.com"
                , "randomstreet"
                , "Patrrick@hotmail.com", "1", "Bikini Bottom", "", "fefe");

        //when

        //then
        Assertions.assertThrows(WrongPasswordException.class,()->librarianController.addLibrarian("Basic MjpTcXVhcmVwYW50cd==",newMemberDto));
    }
    @Test
    void addNewLibrarianWithNonExistingUser_GivesUnknownUserException() {
        //given
        NewMemberDto newMemberDto = new NewMemberDto(Role.MEMBER, "Squarepants", "Patrick", "Patrick@hotmail.com"
                , "randomstreet"
                , "Patrrick@hotmail.com", "1", "Bikini Bottom", "", "fefe");

        //when

        //then
        Assertions.assertThrows(UnknownUserException.class,()->librarianController.addLibrarian("Basic KjpTcXVhcmVwYW50cd==",newMemberDto));
    }

}