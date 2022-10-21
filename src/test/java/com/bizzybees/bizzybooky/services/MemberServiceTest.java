package com.bizzybees.bizzybooky.services;

import com.bizzybees.bizzybooky.domain.Member;
import com.bizzybees.bizzybooky.repositories.MemberRepository;
import com.bizzybees.bizzybooky.services.memberdtos.MemberDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    private MemberRepository memberRepository = new MemberRepository();
    private MemberService memberService = new MemberService(memberRepository);


    @Test
    void givenInvalidEmailAddress_thenThrowIllegalArgumentException() {
        String mailAddress = "patrick.spongebobhotmailcom";

        Assertions.assertThrows(IllegalArgumentException.class, () -> memberService.isValidEmail(mailAddress));
    }

    @Test
    void givenNoEmailAdress_ThrowIllegalArgumentException() {

        MemberDto memberWithNoEmail = new MemberDto("", "", "", null,
                "","", " ","");

        Assertions.assertThrows(IllegalArgumentException.class, () -> memberService.checkRequiredFields(memberWithNoEmail));
    }
    @Test
    void givenNoLastname_ThrowIllegalArgumentException() {

        MemberDto memberWithNoEmail = new MemberDto("", "", "", "randomeamilé@hotmail.com",
                "","", " ","");

        Assertions.assertThrows(IllegalArgumentException.class, () -> memberService.checkRequiredFields(memberWithNoEmail));
    }
    @Test
    void givenNoCity_ThrowIllegalArgumentException() {

        MemberDto memberWithNoEmail = new MemberDto("", "", "", "randomeamilé@hotmail.com",
                "","", " ","");

        Assertions.assertThrows(IllegalArgumentException.class, () -> memberService.checkRequiredFields(memberWithNoEmail));
    }

    @Test
    void givenEmailAddress_thatAlreadyExistsInDatabase() {
        //given
        memberRepository.memberDatabase.put("1",new Member("", "", "patrick.spongebob@hotmail.com", "patrick.spongebob@hotmail.com",
                "","", " "));
        MemberDto alreadyExistingMember = new MemberDto("", "", "", "patrick.spongebob@hotmail.com",
                "","", " ","");
        //when

        //then
        Assertions.assertThrows(IllegalArgumentException.class,()-> memberService.addMember(alreadyExistingMember));
    }

}