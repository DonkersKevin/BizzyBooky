package com.bizzybees.bizzybooky.domain.dto;

import com.bizzybees.bizzybooky.domain.Member;
import com.bizzybees.bizzybooky.domain.memberdtos.NewMemberDto;
import com.bizzybees.bizzybooky.domain.memberdtos.ReturnMemberDto;

public class MemberMapper {
    public Member newMemberDtoToMember(NewMemberDto newMemberDto) {
        return new Member(newMemberDto.getINSS(), newMemberDto.getPassword(), newMemberDto.getLastname(), newMemberDto.getFirstname(), newMemberDto.getEmail(), newMemberDto.getStreetName(), newMemberDto.getStreetNumber(), newMemberDto.getPostalCode(), newMemberDto.getCity());
    }

    public ReturnMemberDto memberToReturnMemberDto(Member member) {
        return new ReturnMemberDto(member.getRole(), member.getINSS(), member.getLastname(), member.getFirstname(), member.getEmail(), member.getStreetName(), member.getStreetNumber(), member.getPostalCode(), member.getCity());


    }
}
