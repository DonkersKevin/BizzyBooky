package com.bizzybees.bizzybooky.controllers;

import com.bizzybees.bizzybooky.domain.Member;
import com.bizzybees.bizzybooky.domain.dto.BookDto;
import com.bizzybees.bizzybooky.repositories.MemberRepository;
import com.bizzybees.bizzybooky.security.Role;
import com.bizzybees.bizzybooky.security.exception.UnauthorizatedException;
import com.bizzybees.bizzybooky.domain.memberdtos.NewMemberDto;
import com.bizzybees.bizzybooky.domain.memberdtos.ReturnMemberDto;
import com.bizzybees.bizzybooky.domain.memberdtos.NewMemberDto;
import com.bizzybees.bizzybooky.domain.memberdtos.ReturnMemberDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import com.bizzybees.bizzybooky.domain.dto.memberdtos.NewMemberDto;
import com.bizzybees.bizzybooky.domain.dto.memberdtos.ReturnMemberDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.Base64;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static io.restassured.RestAssured.port;
import static org.assertj.core.api.Assertions.assertThat;

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