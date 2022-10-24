package com.bizzybees.bizzybooky.controllers;

import com.bizzybees.bizzybooky.domain.BookRental;
import com.bizzybees.bizzybooky.services.MemberService;
import com.bizzybees.bizzybooky.services.RentalService;
import com.bizzybees.bizzybooky.services.memberdtos.MemberDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {
    MemberService memberService;



    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping(path = "add" , consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public MemberDto addMember(@RequestBody MemberDto memberDto) {
        return memberService.addMember(memberDto);
    }



}
