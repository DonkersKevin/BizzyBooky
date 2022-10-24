package com.bizzybees.bizzybooky.repositories;

import com.bizzybees.bizzybooky.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemberRepository {
    public ConcurrentHashMap<String, Member> memberDatabase;


    public MemberRepository() {
        memberDatabase = new ConcurrentHashMap<String, Member>();
//        memberDatabase.put("1",new Member("Squarepants", "Patrick", "Patrick@hotmail.com"
//                , "randomstreet"
//                , "13", "1", "Bikini Bottom"));
//        memberDatabase.put("2",new Member("Squarepants", "Patrick", "Patrick@hotmail.com"
//                , "randomstreet"
//                , "13", "1", "Bikini Bottom"));

    }

    public void save(Member member) {
        memberDatabase.put(member.getINSS(),member);
    }

    public boolean isMemberInDatabase(String memberINSS) {
        return memberDatabase.containsKey(memberINSS);
    }

    public Member getMember(String id) {
        return memberDatabase.get(id);
    }
}
