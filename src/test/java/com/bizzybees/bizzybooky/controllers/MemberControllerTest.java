package com.bizzybees.bizzybooky.controllers;

import com.bizzybees.bizzybooky.repositories.MemberRepository;
import com.bizzybees.bizzybooky.security.Role;
import com.bizzybees.bizzybooky.security.exception.UnauthorizatedException;
import com.bizzybees.bizzybooky.domain.dto.memberdtos.NewMemberDto;
import com.bizzybees.bizzybooky.domain.dto.memberdtos.ReturnMemberDto;
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
        NewMemberDto newMemberDto = new NewMemberDto(Role.MEMBER, "Squarepants", "Patrick", "Patrick@hotmail.com"
                , "randomstreet"
                , "Patrrick@hotmail.com", "1", "Bikini Bottom", "", "fefe");

        //when

        ReturnMemberDto returnMemberDto1 = memberController.addMember(newMemberDto);
        //then
        Assertions.assertTrue(memberRepository.getMemberDatabase().containsKey(returnMemberDto1.getINSS()));
    }
    @Test
    void normalMemberCannotViewMembers(){
        Assertions.assertThrows(UnauthorizatedException.class,()->memberController.getAllmembers("Basic MTpTcXVhcmVwYW50cw=="));
    }



}