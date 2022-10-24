package com.bizzybees.bizzybooky.controllers;

import com.bizzybees.bizzybooky.repositories.MemberRepository;
import com.bizzybees.bizzybooky.services.MemberService;
import com.bizzybees.bizzybooky.services.memberdtos.MemberDto;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.management.MemoryManagerMXBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberControllerTest {
    @Autowired
    private MemberController memberController;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void addNewMemberToRepositoryIsSuccessful() {
        //given
        MemberDto memberDto = new MemberDto("", "Squarepants", "Patrick", "Patrick@hotmail.com"
                , "randomstreet"
                , "13", "1", "Bikini Bottom");

        //when

        MemberDto memberDto1 = memberController.addMember(memberDto);
        //then
        Assertions.assertTrue(memberRepository.memberDatabase.containsKey(memberDto1.getINSS()));
    }
}