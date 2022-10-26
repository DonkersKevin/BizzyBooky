package com.bizzybees.bizzybooky.controllers;

import com.bizzybees.bizzybooky.domain.Member;
import com.bizzybees.bizzybooky.repositories.BookRepository;
import com.bizzybees.bizzybooky.repositories.MemberRepository;
import com.bizzybees.bizzybooky.services.RentalService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

@SpringBootTest
public class RentalControllerTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RentalService rentalService;
    @Autowired
    private BookRepository bookRepository;

    @Test
    void givenLentOutBook_ThenThrowException() {
        //Given
        rentalService.rentBook("1","1000-2000-3000");

        Assertions.assertThrows(NoSuchElementException.class, () -> rentalService.rentBook("1","1000-2000-3000" ));
    }

    @Test
    void CreateABookrentalWithoutValidMember_ThrowsIllegalArgumentException(){
        //given
        memberRepository.save(new Member("Squarepants", "Patrick", "Patrick@hotmail.com"
                , "randomstreet"
                , "13", "1", "Bikini Bottom", "", ""));
        //when

        //then
        Assertions.assertThrows(IllegalArgumentException.class,()->rentalService.rentBook("5","1000-2000-3000"));
    }
}
