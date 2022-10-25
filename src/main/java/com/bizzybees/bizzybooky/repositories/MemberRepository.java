package com.bizzybees.bizzybooky.repositories;

import com.bizzybees.bizzybooky.domain.Member;
import com.bizzybees.bizzybooky.security.Role;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemberRepository {
    private ConcurrentHashMap<String, Member> memberDatabase;


    public MemberRepository() {
        memberDatabase = new ConcurrentHashMap<String, Member>();
        memberDatabase.put("1", new Member("1", "Squarepants", "Patrick"
                , "randomstreet"
                , "Patrick@hotmail.com", "1", "13", "1", "Bikini Bottom"));
        memberDatabase.put("2", new Member("1", "Squarepants", "Patrick"
                , "randomstreet"
                , "Patrick@hotmail.com", "1", "13", "1", "Bikini Bottom"));
        memberDatabase.get("2").setRole(Role.ADMIN);


    }

    public void save(Member member) {
        memberDatabase.put(member.getINSS(), member);
    }

    public boolean isMemberInDatabase(String memberINSS) {
        return memberDatabase.containsKey(memberINSS);
    }

    public Member getMember(String id) {
        return memberDatabase.get(id);
    }

    //For testing purposes with postman
    public ConcurrentHashMap<String, Member> getMemberDatabase() {
        return memberDatabase;
    }
    //For testing purposes with postman
}
