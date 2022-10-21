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
        if (!isValidEmail(memberDto)) {

        }

        memberRepository.save(newMember);
        return memberMapper.memberToMemberDto(newMember);
    }

    private boolean isValidEmail(MemberDto memberDto) {
        if(memberDto.getEmail()==null){
            throw new IllegalArgumentException("Provide an Email address please!");
        }
        return true;
    }



}
