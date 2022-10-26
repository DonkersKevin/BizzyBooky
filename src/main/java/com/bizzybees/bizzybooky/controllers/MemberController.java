package com.bizzybees.bizzybooky.controllers;

import com.bizzybees.bizzybooky.domain.Member;
import com.bizzybees.bizzybooky.security.Feature;
import com.bizzybees.bizzybooky.security.SecurityService;
import com.bizzybees.bizzybooky.services.MemberService;
import com.bizzybees.bizzybooky.domain.dto.memberdtos.NewMemberDto;
import com.bizzybees.bizzybooky.domain.dto.memberdtos.ReturnMemberDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    MemberService memberService;
    SecurityService securityService;

    public MemberController(MemberService memberService, SecurityService securityService) {
        this.memberService = memberService;
        this.securityService = securityService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReturnMemberDto addMember(@RequestBody NewMemberDto newMemberDto) {
        log.info("adding the following member: "+ newMemberDto);
        return memberService.addMember(newMemberDto);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/view", produces = MediaType.APPLICATION_JSON_VALUE)
    public ConcurrentHashMap<String, Member> getAllmembers(@RequestHeader String authorization) {
        log.info("Retrieving the list of all registered members" );
        securityService.validateAuthorization(authorization, Feature.VIEW_MEMBERS);
        return memberService.getAllMembers();
        //ToDo Exclude INSS adn also return memberDto

    }


}
