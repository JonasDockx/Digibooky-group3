package com.switchfully.digibookygroup3.domain.member;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemberRepository {

    private final ConcurrentHashMap<String, Member> members;

    public MemberRepository() {
        members = new ConcurrentHashMap<>();
    }

    public void addMember(Member member) {
        members.put(member.getId(), member);
    }

    public List<Member> getAllMembers() {

        return members.values()
                .stream()
                .toList();
    }

    public boolean isINSSPresent(String inss) {
        return members.values().stream().
                map(Member::getInss).
                anyMatch(each -> each.equals(inss));
    }

    public boolean isEmailPresent(String email) {
        return members.values().stream().
                map(Member::getEmail).
                anyMatch(each -> each.equals(email));
    }
}
