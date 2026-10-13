package com.switchfully.digibookygroup3.service;

import com.switchfully.digibookygroup3.api.MemberMapper;
import com.switchfully.digibookygroup3.api.dtos.CreateMemberDTO;
import com.switchfully.digibookygroup3.domain.member.Member;
import com.switchfully.digibookygroup3.domain.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    private final MemberRepository memberRepository = new MemberRepository();
    private final MemberMapper memberMapper = new MemberMapper();
    private final MemberService memberService = new MemberService(memberRepository, memberMapper);

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
    void checkIfThirdAddedMemberIsAddedToRepository() {
        CreateMemberDTO member = new CreateMemberDTO(
                "345678901",
                "Martens",
                "Matthieu",
                "Matthieu@Test.com",
                "Test Street",
                "123",
                "3456",
                "Brussels");



        Member result = memberService.addMember(member);

        assertTrue(memberRepository.getAllMembers().contains(result));
        assertEquals(3, memberRepository.getAllMembers().size());
    }
}