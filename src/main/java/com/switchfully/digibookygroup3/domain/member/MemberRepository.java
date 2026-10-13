package com.switchfully.digibookygroup3.domain.member;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberRepository {

    private final List<Member> members;

    public MemberRepository() {
        members = new ArrayList<>();
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public List<Member> getAllMembers() {
        return members;
    }

    public boolean isINSSUnique(String inss) {
        return members.stream().
                map(member -> member.getINSS()).
                anyMatch(each -> each.equals(inss));
    }

    public boolean isEmailUnique(String email) {
        return members.stream().
                map(member -> member.getEmail()).
                anyMatch(each -> each.equals(email));
    }
}
