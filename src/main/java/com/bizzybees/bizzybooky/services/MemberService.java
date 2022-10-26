package com.bizzybees.bizzybooky.services;

import com.bizzybees.bizzybooky.domain.Member;
import com.bizzybees.bizzybooky.domain.dto.memberDtos.MemberMapper;
import com.bizzybees.bizzybooky.repositories.MemberRepository;
import com.bizzybees.bizzybooky.security.Role;
import com.bizzybees.bizzybooky.domain.dto.memberDtos.NewMemberDto;
import com.bizzybees.bizzybooky.domain.dto.memberDtos.ReturnMemberDto;
import com.bizzybees.bizzybooky.services.util.MemberValidator;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


@Service
public class MemberService {
    MemberRepository memberRepository;
    MemberMapper memberMapper;
    MemberValidator memberValidator;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        this.memberMapper = new MemberMapper();
        this.memberValidator = new MemberValidator();
    }

    public ReturnMemberDto addMember(NewMemberDto newMemberDto) {
        memberValidator.checkRequiredFields(newMemberDto);
        memberValidator.isValidEmail(newMemberDto.getEmail());
        isUniqueEmail(newMemberDto.getEmail());
        isUniqueInss(newMemberDto.getINSS());
        Member newMember = memberMapper.newMemberDtoToMember(newMemberDto);
        memberRepository.save(newMember);
        return memberMapper.memberToReturnMemberDto(newMember);
    }


    public ReturnMemberDto addLibrarian(NewMemberDto newMemberDto) {
        memberValidator.checkRequiredFields(newMemberDto);
        memberValidator.isValidEmail(newMemberDto.getEmail());
        isUniqueEmail(newMemberDto.getEmail());
        Member newMember = memberMapper.newMemberDtoToMember(newMemberDto);
        newMember.setRole(Role.LIBRARIAN);
        memberRepository.save(newMember);
        return memberMapper.memberToReturnMemberDto(newMember);
    }

    private void isUniqueInss(String inss) {
        if (memberRepository.getMemberDatabase().values().stream().anyMatch(member -> inss.equals(member.getINSS()))) {
            throw new IllegalArgumentException("INSS Already exists");
        }
    }

    // ToDo make unit test for exception
    public void isUniqueEmail(String email) {
        if (memberRepository.getMemberDatabase().values().stream().anyMatch(member -> email.equals(member.getEmail()))) {
            throw new IllegalArgumentException("Email Already exists");
        }
    }


    //GetMembers method for testing purposes
    public List<ReturnMemberDto> getAllMembers() {

        return memberRepository.getMemberDatabase().values().stream()
                .map(member -> memberMapper.memberToReturnMemberDto(member))
                .toList();
    }


}
