package com.switchfully.digibookygroup3.domain.user;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {

    private final ConcurrentHashMap<String, User> users;

    public UserRepository() {
        users = new ConcurrentHashMap<>();
//        As requested, an admin exists on startup.
        addUser(new Admin("admin", "admin", "admin@admin.com", "pass"));
        addUser(new Member(
                "member",
                "member",
                "member",
                "member@member.com",
                "member",
                "member",
                "member",
                "member",
                "pass"));
    }

    public void addUser(User user) {
        users.put(user.getId(), user);
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

    public Optional<User> getUserByEmail(String email) {
        return users.values().stream().
                filter(each -> each.getEmail().equals(email)).
                findFirst();

    }

    public boolean passMatch(String id, String password) {
        return users.get(id).getPassword().equals(password);

    }
}
