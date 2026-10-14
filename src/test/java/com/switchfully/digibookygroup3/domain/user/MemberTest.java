package com.switchfully.digibookygroup3.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    private final UserRepository userRepository = new UserRepository();

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
                "pass"
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
                "sgsg"
                )
        );
    }

    @Test
    void checkIfINSSIsAlreadyPresent_thenReturnTrue() {
        boolean result = userRepository.isINSSPresent("123456789");

        assertTrue(result);
    }

    @Test
    void checkIfINSSIsNotPresent_thenReturnFalse() {
        boolean result = userRepository.isINSSPresent("000000");

        assertFalse(result);
    }

    @Test
    void checkIfEmailIsAlreadyPresent_thenReturnTrue() {
        boolean result = userRepository.isEmailPresent("jonas@test.com");

        assertTrue(result);
    }

    @Test
    void checkIfEmailIsNotPresent_thenReturnFalse() {
        boolean result = userRepository.isEmailPresent("jonas@jonas.com");

        assertFalse(result);
    }

}