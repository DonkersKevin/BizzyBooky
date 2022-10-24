package com.bizzybees.bizzybooky.controllers;

import com.bizzybees.bizzybooky.services.MemberService;
import com.bizzybees.bizzybooky.services.memberdtos.NewMemberDto;
import com.bizzybees.bizzybooky.services.memberdtos.ReturnMemberDto;
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
    public ReturnMemberDto addMember(@RequestBody NewMemberDto newMemberDto) {
        return memberService.addMember(newMemberDto);
    }


}
