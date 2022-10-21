package com.bizzybees.bizzybooky.services;

import com.bizzybees.bizzybooky.domain.Member;
import com.bizzybees.bizzybooky.repositories.MemberRepository;
import com.bizzybees.bizzybooky.services.memberdtos.MemberDto;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    MemberRepository memberRepository;
    MemberMapper memberMapper;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        this.memberMapper = new MemberMapper();
    }

    public MemberDto addMember(MemberDto memberDto){
        Member newMember = memberMapper.memberDtoToMember(memberDto);
        memberRepository.save(newMember);
        return memberMapper.memberToMemberDto(newMember);
    }
}
