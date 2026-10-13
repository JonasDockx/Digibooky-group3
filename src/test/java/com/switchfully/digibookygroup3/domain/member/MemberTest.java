package com.switchfully.digibookygroup3.domain.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    private final MemberRepository memberRepository = new MemberRepository();

    @BeforeEach
    void setUp() {
        memberRepository.addMember(new Member(
                "123456789",
                "Dockx",
                "Jonas",
                "jonas@test.com",
                "Street street",
                "123",
                "1234",
                "Brussels"
        ));
        memberRepository.addMember(new Member(
                "234567890",
                "Figueroa",
                "Francisco",
                "francisco@test.com",
                "Street street",
                "345",
                "1234",
                "Brussels"
                )
        );
    }

    @Test
    void checkIfINSSIsAlreadyPresent_thenReturnTrue() {
        boolean result = memberRepository.isINSSPresent("123456789");

        assertTrue(result);
    }

    @Test
    void checkIfINSSIsNotPresent_thenReturnFalse() {
        boolean result = memberRepository.isINSSPresent("000000");

        assertFalse(result);
    }

    @Test
    void checkIfEmailIsAlreadyPresent_thenReturnTrue() {
        boolean result = memberRepository.isEmailPresent("jonas@test.com");

        assertTrue(result);
    }

    @Test
    void checkIfEmailIsNotPresent_thenReturnFalse() {
        boolean result = memberRepository.isEmailPresent("jonas@jonas.com");

        assertFalse(result);
    }

}