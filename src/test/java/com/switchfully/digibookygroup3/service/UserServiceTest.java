package com.switchfully.digibookygroup3.service;

import com.switchfully.digibookygroup3.mapper.MemberMapper;
import com.switchfully.digibookygroup3.api.dtos.CreateLibrarianDTO;
import com.switchfully.digibookygroup3.api.dtos.CreateMemberDTO;
import com.switchfully.digibookygroup3.api.dtos.LibrarianDTO;
import com.switchfully.digibookygroup3.api.dtos.MemberDTO;
import com.switchfully.digibookygroup3.domain.user.Member;
import com.switchfully.digibookygroup3.domain.user.UserRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceTest {

    @LocalServerPort
    private int port;
    private static final String ADMIN_USERNAME = "admin@admin.com";
    private static final String ADMIN_PASSWORD = "pass";
    private static final String BASE_URL = "http://localhost:";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MemberMapper memberMapper;

    @BeforeEach
    void setupUserRepository() {

        userRepository.resetMembers();

        for(int i = 0; i < 4; ++i){
            userRepository.addUser(
                    new Member(
                            "member" + i,
                            "member" + i,
                            "member" + i,
                            "member" + i + "@member.com",
                            "member" + i,
                            "member" + i,
                            "member" + i,
                            "member" + i,
                            "pass")
            );
        }
    }

    @Test
    void givenTheCorrectRequestForAddingAMember_thenMemberAddedResponse201(){
        CreateMemberDTO newMember =
                new CreateMemberDTO(
                        "member",
                        "member",
                        "member",
                        "member@member.com",
                        "member",
                        "member",
                        "member",
                        "member",
                        "pass"
                );

                given()
                .contentType(ContentType.JSON)
                .body(newMember)
                .when()
                .post("http://localhost:" + port + "/members")
                .then()
                .statusCode(201);

        assertThat(userRepository.getUserByEmail("member@member.com")).isPresent();
        assertThat(userRepository.getAllMembers()).hasSize(5);

        MemberDTO[] members = given()
                .auth()
                .preemptive()
                .basic(ADMIN_USERNAME, ADMIN_PASSWORD)
                .when()
                .get("http://localhost:" + port + "/members")
                .then()
                .statusCode(200)
                .extract()
                .as(MemberDTO[].class);

        assertThat(members)
                .isNotNull()
                .isNotEmpty()
                .hasSize(5);

        given()
                .auth()
                .preemptive()
                .basic("member@member.com", "pass")
                .when()
                .get("http://localhost:" + port + "/members")
                .then()
                .statusCode(403);
    }

    @Test
    void getAllMembers_whenGivenARepositoryOfFourMembers_thenReturnArrayOfFourMembers() {
        MemberDTO[] members = given()
                .auth()
                .preemptive()
                .basic(ADMIN_USERNAME, ADMIN_PASSWORD)
                .when()
                .get("http://localhost:" + port + "/members")
                .then()
                .statusCode(200)
            .extract()
                .as(MemberDTO[].class);

            assertThat(members)
                    .isNotNull()
                    .isNotEmpty()
                    .hasSize(4);
    }

    @Test
    void getAllMembers_givenTheRightCredentials_thenResponseIs200() {
        given()
                .auth()
                .preemptive()
                .basic(ADMIN_USERNAME, ADMIN_PASSWORD)
                .when()
                .get("http://localhost:" + port + "/members")
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
                .get("http://localhost:" + port + "/members")
                .then()
                .statusCode(401);
    }

    @Test
    void getAllMembers_givenTheRightCredentialsButNotAdmin_thenResponseIs403() {
        given()
                .auth()
                .preemptive()
                .basic("member1@member.com", "pass")
                .when()
                .get("http://localhost:" + port + "/members")
                .then()
                .statusCode(403);
    }

    @Test
    void createLibrarian_givenCorrectCredentialsAndBody_thenCreateALibrarian() {
        CreateLibrarianDTO createLibrarianDTO = new CreateLibrarianDTO(
                "Librarian",
                "Mister",
                "librarian@libarian.com",
                "pass"
        );

        LibrarianDTO librarian = given()
                .contentType(ContentType.JSON)
                .body(createLibrarianDTO)
                .auth()
                .preemptive()
                .basic(ADMIN_USERNAME, ADMIN_PASSWORD)
                .when()
                .post(BASE_URL + port + "/members/librarians")
                .then()
                .statusCode(201)
                .extract()
                .as(LibrarianDTO.class);

        assertThat(librarian).isNotNull();
        assertThat(librarian.getEmail().equals("librarian@libarian.com"));
    }

    @Test
    void createLibrarian_givenWrongCredentials_thenReturn401Response() {
        CreateLibrarianDTO createLibrarianDTO = new CreateLibrarianDTO(
                "Librarian",
                "Mister",
                "librarian@libarian.com",
                "pass"
        );

        given()
                .contentType(ContentType.JSON)
                .body(createLibrarianDTO)
                .auth()
                .preemptive()
                .basic(ADMIN_USERNAME, "wrong password")
                .when()
                .post(BASE_URL + port + "/members/librarians")
                .then()
                .statusCode(401);
    }

    @Test
    void createLibrarian_givenMemberThatTriesToCall_thenReturn403Response() {
        CreateLibrarianDTO createLibrarianDTO = new CreateLibrarianDTO(
                "Librarian",
                "Mister",
                "librarian@libarian.com",
                "pass"
        );

        Member member = userRepository.getAllMembers().get(0);

        given()
                .contentType(ContentType.JSON)
                .body(createLibrarianDTO)
                .auth()
                .preemptive()
                .basic(member.getEmail(), member.getPassword())
                .when()
                .post(BASE_URL + port + "/members/librarians")
                .then()
                .statusCode(403);
    }
}