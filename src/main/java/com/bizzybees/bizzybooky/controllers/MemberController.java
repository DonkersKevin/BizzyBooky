package com.bizzybees.bizzybooky.controllers;

import com.bizzybees.bizzybooky.domain.Member;
import com.bizzybees.bizzybooky.security.Feature;
import com.bizzybees.bizzybooky.security.SecurityService;
import com.bizzybees.bizzybooky.services.MemberService;
import com.bizzybees.bizzybooky.domain.memberdtos.NewMemberDto;
import com.bizzybees.bizzybooky.domain.memberdtos.ReturnMemberDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/members")
public class MemberController {
    MemberService memberService;
    SecurityService securityService;

    public MemberController(MemberService memberService, SecurityService securityService) {
        this.memberService = memberService;
        this.securityService = securityService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReturnMemberDto addMember(@RequestBody NewMemberDto newMemberDto) {
        return memberService.addMember(newMemberDto);
    }

    //For Testing creating members
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/view", produces = MediaType.APPLICATION_JSON_VALUE)
    public ConcurrentHashMap<String, Member> getAllmembers(@RequestHeader String authorization) {
        securityService.validateAuthorization(authorization, Feature.VIEW_MEMBERS);
        return memberService.getAllMembers();

    }
    //for Testing creating members

}
