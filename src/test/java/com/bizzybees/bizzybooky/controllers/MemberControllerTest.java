package com.bizzybees.bizzybooky.controllers;

import com.bizzybees.bizzybooky.repositories.MemberRepository;
import com.bizzybees.bizzybooky.services.memberdtos.NewMemberDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberControllerTest {
    @Autowired
    private MemberController memberController;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void addNewMemberToRepositoryIsSuccessful() {
        //given
        NewMemberDto newMemberDto = new NewMemberDto("", "Squarepants", "Patrick", "Patrick@hotmail.com"
                , "randomstreet"
                , "13", "1", "Bikini Bottom");

        //when

        NewMemberDto newMemberDto1 = memberController.addMember(newMemberDto);
        //then
        Assertions.assertTrue(memberRepository.memberDatabase.containsKey(newMemberDto1.getINSS()));
    }
}