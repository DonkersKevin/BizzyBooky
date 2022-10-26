package com.bizzybees.bizzybooky.controllers;


import com.bizzybees.bizzybooky.domain.Member;
import com.bizzybees.bizzybooky.exceptions.AccessDeniedException;
import com.bizzybees.bizzybooky.repositories.MemberRepository;
import com.bizzybees.bizzybooky.security.Role;


import com.bizzybees.bizzybooky.domain.dto.memberDtos.NewMemberDto;
import com.bizzybees.bizzybooky.domain.dto.memberDtos.ReturnMemberDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class MemberControllerTest {
    @LocalServerPort
    private int port;
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
        Assertions.assertThrows(AccessDeniedException.class,()->memberController.getAllmembers("Basic MTpTcXVhcmVwYW50cw=="));
    }

    @Test
    void viewMembershappyPath(){
        //given
        ConcurrentHashMap<String, Member> expectedDatabase;
        expectedDatabase = new ConcurrentHashMap<String, Member>();
        expectedDatabase.put("1", new Member("1", "Squarepants", "Patrick"
                , "randomstreet"
                , "Patrick@hotmail.com", "1", "13", "1", "Bikini Bottom"));
        expectedDatabase.put("2", new Member("1", "Squarepants", "Patrick"
                , "randomstreet"
                , "Patrick@hotmail.com", "1", "13", "1", "Bikini Bottom"));
        expectedDatabase.get("2").setRole(Role.ADMIN);
        //when
        ConcurrentHashMap result = RestAssured
                .given()
                .auth()
                .preemptive()
                .basic("2","Squarepants")
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .get("/members/view")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(ConcurrentHashMap.class);
        //then
        assertThat(List.of(result).equals(List.of(expectedDatabase)));
    }
    @Test
    void viewMembersByMemberGivesAccessDeniedException(){
        //given
        ConcurrentHashMap<String, Member> expectedDatabase;
        expectedDatabase = new ConcurrentHashMap<String, Member>();
        expectedDatabase.put("1", new Member("1", "Squarepants", "Patrick"
                , "randomstreet"
                , "Patrick@hotmail.com", "1", "13", "1", "Bikini Bottom"));
        expectedDatabase.put("2", new Member("1", "Squarepants", "Patrick"
                , "randomstreet"
                , "Patrick@hotmail.com", "1", "13", "1", "Bikini Bottom"));
        expectedDatabase.get("2").setRole(Role.ADMIN);
        //when
        ConcurrentHashMap result = RestAssured
                .given()
                .auth()
                .preemptive()
                .basic("1","Squarepants")
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .get("/members/view")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .extract()
                .as(ConcurrentHashMap.class);

    }




}