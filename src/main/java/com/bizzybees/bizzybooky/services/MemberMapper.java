package com.bizzybees.bizzybooky.services;

import com.bizzybees.bizzybooky.domain.Member;
import com.bizzybees.bizzybooky.services.memberdtos.MemberDto;

public class MemberMapper {
    public Member memberDtoToMember(MemberDto memberDto) {
        return new Member(memberDto.getLastname(), memberDto.getFirstname(), memberDto.getEmail(), memberDto.getStreetName(), memberDto.getStreetNumber(), memberDto.getPostalCode(), memberDto.getCity());
    }

    public MemberDto memberToMemberDto(Member member) {
        return new MemberDto(member.getINSS(), member.getLastname(), member.getFirstname(), member.getEmail(), member.getStreetName(), member.getStreetNumber(), member.getPostalCode(), member.getCity());


    }
}
