package com.bizzybees.bizzybooky.controllers;

import com.bizzybees.bizzybooky.services.MemberService;
import com.bizzybees.bizzybooky.services.memberdtos.MemberDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {
    MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    public MemberDto addMember(MemberDto memberDto) {
        return memberService.addMember(memberDto);
    }


}
