package com.bizzybees.bizzybooky.controllers;

import com.bizzybees.bizzybooky.repositories.MemberRepository;
import com.bizzybees.bizzybooky.security.Role;
import com.bizzybees.bizzybooky.services.memberdtos.NewMemberDto;
import com.bizzybees.bizzybooky.services.memberdtos.ReturnMemberDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Base64;

@SpringBootTest
class MemberControllerTest {
    @Autowired
    private MemberController memberController;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void addNewMemberToRepositoryIsSuccessful() {
        //given
        NewMemberDto newMemberDto = new NewMemberDto(Role.MEMBER, "Squarepants", "Patrick", "Patrick@hotmail.com"
                , "randomstreet"
                , "Patric@hotmail.com", "1", "Bikini Bottom", "", "fefe");

        //when

        ReturnMemberDto returnMemberDto1 = memberController.addMember(newMemberDto);
        //then
        Assertions.assertTrue(memberRepository.memberDatabase.containsKey(returnMemberDto1.getINSS()));
    }
    @Test
    void addNewLibrarianToRepositoryIsSuccessful() {
        //given
        NewMemberDto newMemberDto = new NewMemberDto(Role.ADMIN, "Squarepants", "Patrick", "Patrick@hotmail.com"
                , "randomstreet"
                , "Patric@hotmail.com", "1", "Bikini Bottom", "", "fefe");




        //when

        //ReturnMemberDto returnMemberDto1 = memberController.addLibrarian(,newMemberDto);
        //then
       // Assertions.assertTrue(memberRepository.memberDatabase.containsKey(returnMemberDto1.getINSS()));
    }
}