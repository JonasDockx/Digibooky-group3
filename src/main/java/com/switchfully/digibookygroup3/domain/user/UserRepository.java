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
        resetMembers();
    }

    public void addUser(User user) {
        users.put(user.getId(), user);
    }

    public List<Member> getAllMembers() {

        return users.values()
                .stream()
                .filter(user -> user.getUserType().equals(UserType.MEMBER))
                .map(Member.class::cast)
                .toList();
    }

    public boolean isINSSPresent(String inss) {
        return users.values().stream()
                .filter(user -> user.getUserType().equals(UserType.MEMBER))
                .map(Member.class::cast)
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

    public boolean doesPasswordMatch(String id, String password) {
        return users.get(id).getPassword().equals(password);

    }

    public void resetMembers() {
        users.clear();
        //        As requested, an admin exists on startup.
        addUser(User.createAdmin("admin", "admin", "admin@admin.com", "pass"));
    }
}
