package com.switchfully.digibookygroup3.domain.user;

import org.springframework.stereotype.Repository;

import javax.swing.plaf.metal.MetalMenuBarUI;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {

    private final ConcurrentHashMap<String, User> users;

    public UserRepository() {
        users = new ConcurrentHashMap<>();
    }

    public void addMember(Member member) {
        users.put(member.getId(), member);
    }

    public List<Member> getAllMembers() {

        return users.values()
                .stream()
                .filter(user -> user.getUserType().equals(UserType.MEMBER))
                .map(user -> (Member) user)
                .toList();
    }

    public boolean isINSSPresent(String inss) {
        return users.values().stream()
                .filter(user -> user.getUserType().equals(UserType.MEMBER))
                .map(user -> (Member) user)
                .map(Member::getInss)
                .anyMatch(each -> each.equals(inss));
    }

    public boolean isEmailPresent(String email) {
        return users.values().stream().
                map(User::getEmail).
                anyMatch(each -> each.equals(email));
    }
}
