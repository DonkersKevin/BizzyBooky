package com.bizzybees.bizzybooky.domain.dto.memberDtos;

import com.bizzybees.bizzybooky.domain.Member;

public class MemberMapper {
    public Member newMemberDtoToMember(NewMemberDto newMemberDto) {
        return new Member(newMemberDto.getINSS(), newMemberDto.getPassword(), newMemberDto.getLastname(), newMemberDto.getFirstname(), newMemberDto.getEmail(), newMemberDto.getStreetName(), newMemberDto.getStreetNumber(), newMemberDto.getPostalCode(), newMemberDto.getCity());
    }

    public ReturnMemberDto memberToReturnMemberDto(Member member) {
        return new ReturnMemberDto(member.getRole(), member.getINSS(), member.getLastname(), member.getFirstname(), member.getEmail(), member.getStreetName(), member.getStreetNumber(), member.getPostalCode(), member.getCity());


    }
}
