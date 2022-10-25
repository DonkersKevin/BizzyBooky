package com.bizzybees.bizzybooky.controllers;

import com.bizzybees.bizzybooky.domain.Member;
import com.bizzybees.bizzybooky.security.Feature;
import com.bizzybees.bizzybooky.security.SecurityService;
import com.bizzybees.bizzybooky.services.MemberService;
import com.bizzybees.bizzybooky.domain.dto.memberdtos.NewMemberDto;
import com.bizzybees.bizzybooky.domain.dto.memberdtos.ReturnMemberDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/librarians")
public class LibrarianController {
    MemberService memberService;
    SecurityService securityService;

    public LibrarianController(MemberService memberService, SecurityService securityService) {
        this.memberService = memberService;
        this.securityService = securityService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReturnMemberDto addLibrarian(@RequestHeader String authorization, @RequestBody NewMemberDto newMemberDto) {
        securityService.validateAuthorization(authorization, Feature.REGISTER_LIBRARIAN);
        return memberService.addLibrarian(newMemberDto);
    }

}
