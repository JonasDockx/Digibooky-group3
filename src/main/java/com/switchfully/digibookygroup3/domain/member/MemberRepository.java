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

    public boolean isINSSPresent(String inss) {
        return members.stream().
                map(Member::getINSS).
                anyMatch(each -> each.equals(inss));
    }

    public boolean isEmailPresent(String email) {
        return members.stream().
                map(Member::getEmail).
                anyMatch(each -> each.equals(email));
    }
}
