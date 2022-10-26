package com.bizzybees.bizzybooky.services;

import com.bizzybees.bizzybooky.domain.Member;
import com.bizzybees.bizzybooky.repositories.BookRepository;
import com.bizzybees.bizzybooky.repositories.MemberRepository;
import com.bizzybees.bizzybooky.repositories.RentalRepository;
import com.bizzybees.bizzybooky.security.Role;
import com.bizzybees.bizzybooky.domain.dto.memberdtos.NewMemberDto;
import com.bizzybees.bizzybooky.services.util.MemberValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MemberServiceTest {
    private MemberRepository memberRepository = new MemberRepository();
    private MemberService memberService = new MemberService(memberRepository);
    private MemberValidator memberValidator = new MemberValidator();

    private RentalService rentalService = new RentalService(new RentalRepository(),new BookRepository(),memberRepository,new BookService(new BookRepository()));


    @Test
    void givenInvalidEmailAddress_thenThrowIllegalArgumentException() {
        String mailAddress = "patrick.spongebobhotmailcom";

        Assertions.assertThrows(IllegalArgumentException.class, () -> memberValidator.isValidEmail(mailAddress));
    }

    @Test
    void givenNoEmailAdress_ThrowIllegalArgumentException() {

        NewMemberDto memberWithNoEmail = new NewMemberDto(Role.MEMBER, "", "", null,
                "","", " ","", "", "");

        Assertions.assertThrows(IllegalArgumentException.class, () -> memberValidator.checkRequiredFields(memberWithNoEmail));
    }
    @Test
    void givenNoLastname_ThrowIllegalArgumentException() {

        NewMemberDto memberWithNoEmail = new NewMemberDto(Role.MEMBER, "", "", "",
                "","randomeamilé@hotmail.com", " ","", "", "");

        Assertions.assertThrows(IllegalArgumentException.class, () -> memberValidator.checkRequiredFields(memberWithNoEmail));
    }
    @Test
    void givenNoCity_ThrowIllegalArgumentException() {

        NewMemberDto memberWithNoEmail = new NewMemberDto(Role.MEMBER, "", "", "randomeamilé@hotmail.com",
                "","", " ","", "", "");

        Assertions.assertThrows(IllegalArgumentException.class, () -> memberValidator.checkRequiredFields(memberWithNoEmail));
    }

    @Test
    void givenEmailAddress_thatAlreadyExistsInDatabase() {
        //given
        memberRepository.save(new Member("1", "", "ffe", "fe",
                "patrick.spongebob@hotmail.com","", " ", "", ""));
        NewMemberDto alreadyExistingMember = new NewMemberDto(Role.MEMBER, "", "", "patrick.spongebob@hotmail.com",
                "","patrick.spongebob@hotmail.com", " ","", "", "");
        //when

        //then
        Assertions.assertThrows(IllegalArgumentException.class,()-> memberService.addMember(alreadyExistingMember));
    }

    @Test
    void givenInss_thatAlreadyExistsInDatabase() {
        //given
        memberRepository.save(new Member("1", "fe", "ffe", "fe",
                "patrick.spongebob@hotmail.com","e", "e ", "e", "ee"));
        NewMemberDto alreadyExistingMember = new NewMemberDto(Role.MEMBER, "ee", "1", "ee",
                "e","patrbob@hotmail.com", "e ","", "e", "ee");
        //when

        //then
        Assertions.assertThrows(IllegalArgumentException.class,()-> memberService.addMember(alreadyExistingMember));
    }


}