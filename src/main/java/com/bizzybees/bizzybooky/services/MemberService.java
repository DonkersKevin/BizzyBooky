package com.bizzybees.bizzybooky.services;

import com.bizzybees.bizzybooky.domain.Member;
import com.bizzybees.bizzybooky.repositories.MemberRepository;
import com.bizzybees.bizzybooky.services.memberdtos.NewMemberDto;
import com.bizzybees.bizzybooky.services.memberdtos.ReturnMemberDto;
import org.springframework.stereotype.Service;


@Service
public class MemberService {
    MemberRepository memberRepository;
    MemberMapper memberMapper;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        this.memberMapper = new MemberMapper();
    }

    public ReturnMemberDto addMember(NewMemberDto newMemberDto) {
        checkRequiredFields(newMemberDto);
        isValidEmail(newMemberDto.getEmail());
        isUniqueEmail(newMemberDto.getEmail());
        Member newMember = memberMapper.newMemberDtoToMember(newMemberDto);
        memberRepository.save(newMember);
        return memberMapper.memberToReturnMemberDto(newMember);
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

    public void checkRequiredFields(NewMemberDto newMemberDto) {
        if (newMemberDto.getEmail() == null || newMemberDto.getEmail().equals("")) {
            throw new IllegalArgumentException("Provide an Email address please!");
        }
        if (newMemberDto.getCity() == null || newMemberDto.getCity().equals("")) {
            throw new IllegalArgumentException("Provide a City please!");
        }
        if (newMemberDto.getLastname() == null || newMemberDto.getLastname().equals("")) {
            throw new IllegalArgumentException("Provide a lastname please!");
        }
        if (newMemberDto.getINSS() == null || newMemberDto.getINSS().equals("")) {
            throw new IllegalArgumentException("Provide an INSS number please!");
        }
    }


}
