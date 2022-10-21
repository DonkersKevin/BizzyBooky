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

    public MemberDto addMember(MemberDto memberDto) {
        checkRequiredFields(memberDto);
        isValidEmail(memberDto.getEmail());
        isUniqueEmail(memberDto.getEmail());
        Member newMember = memberMapper.memberDtoToMember(memberDto);
        memberRepository.save(newMember);
        return memberMapper.memberToMemberDto(newMember);
    }

    // Public for testing
    public void isValidEmail(String emailAddress) {

        if (!emailAddress.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
            throw new IllegalArgumentException("Email address is not valid");
        }

    }

    public void isUniqueEmail(String email) {
        if (memberRepository.memberDatabase.values().stream().anyMatch(member -> email.equals(member.getEmail()))) {
            throw new IllegalArgumentException("Email Already exists");
        }
    }

    public void checkRequiredFields(MemberDto memberDto) {
        if (memberDto.getEmail() == null || memberDto.getEmail().equals("")) {
            throw new IllegalArgumentException("Provide an Email address please!");
        }
        if (memberDto.getCity() == null || memberDto.getCity().equals("")) {
            throw new IllegalArgumentException("Provide a City please!");
        }
        if (memberDto.getLastname() == null || memberDto.getLastname().equals("")) {
            throw new IllegalArgumentException("Provide a lastname please!");
        }
    }


}
