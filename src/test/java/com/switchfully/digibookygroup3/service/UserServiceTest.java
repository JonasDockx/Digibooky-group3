package com.switchfully.digibookygroup3.service;

import com.switchfully.digibookygroup3.api.MemberMapper;
import com.switchfully.digibookygroup3.api.dtos.CreateMemberDTO;
import com.switchfully.digibookygroup3.domain.user.Member;
import com.switchfully.digibookygroup3.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private final UserRepository userRepository = new UserRepository();
    private final MemberMapper memberMapper = new MemberMapper();
    private final UserService userService = new UserService(userRepository, memberMapper);

    @BeforeEach
    void setUp() {
        userRepository.addUser(new Member(
                "123456789",
                "Dockx",
                "Jonas",
                "jonas@test.com",
                "Street street",
                "123",
                "1234",
                "Brussels",
                "oenzfo"
        ));
        userRepository.addUser(new Member(
                        "234567890",
                        "Figueroa",
                        "Francisco",
                        "francisco@test.com",
                        "Street street",
                        "345",
                        "1234",
                        "Brussels",
                        "efboze"
                )
        );
    }

    @Test
    void checkIfThirdAddedMemberIsAddedToRepository() {
        CreateMemberDTO member = new CreateMemberDTO(
                "345678901",
                "Martens",
                "Matthieu",
                "Matthieu@Test.com",
                "Test Street",
                "123",
                "3456",
                "Brussels",
                "ékjbfzeo"
        );


        Member result = userService.addMember(member);

        assertTrue(userRepository.getAllMembers().contains(result));
        assertEquals(3, userRepository.getAllMembers().size());
    }
}