package com.bizzybees.bizzybooky.repositories;

import com.bizzybees.bizzybooky.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemberRepository {
    ConcurrentHashMap<String, Member> memberDatabase;

    public MemberRepository() {
        this.memberDatabase = new ConcurrentHashMap<>();
    }

    public void save(Member member) {
        memberDatabase.put(member.getINSS(),member);
    }
}
