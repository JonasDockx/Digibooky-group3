package com.switchfully.digibookygroup3.service;

import com.switchfully.digibookygroup3.api.MemberMapper;
import com.switchfully.digibookygroup3.api.dtos.CreateMemberDTO;
import com.switchfully.digibookygroup3.api.dtos.MemberDTO;
import com.switchfully.digibookygroup3.domain.user.Member;
import com.switchfully.digibookygroup3.domain.user.UserRepository;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private final static String ADMIN_USERNAME = "admin@admin.com";
    private final static String ADMIN_PASSWORD = "pass";

    private final UserRepository userRepository = new UserRepository();
    private final MemberMapper memberMapper = new MemberMapper();
    private final UserService userService = new UserService(userRepository, memberMapper);


    @Test
    void getAllMembers_givenTheRightCredentials_thenResponseIs200() {
        given()
                .auth()
                .preemptive()
                .basic(ADMIN_USERNAME, ADMIN_PASSWORD)
                .when()
                .get("http://localhost:8080/members")
                .then()
                .statusCode(200);
    }

    @Test
    void getAllMembers_givenTheWrongCredentials_thenResponseIs401() {
        given()
                .auth()
                .preemptive()
                .basic(ADMIN_USERNAME, "lknkdfg")
                .when()
                .get("http://localhost:8080/members")
                .then()
                .statusCode(401);
    }



//                .extract()
//                .as(MemberDTO[].class);
//
//        assertThat(members)
//                .isNotNull()
//                .isNotEmpty()
//                .hasSize(1);

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
        assertEquals(4, userRepository.getAllMembers().size());
    }
}