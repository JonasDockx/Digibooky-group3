package com.switchfully.digibookygroup3.api;

import com.switchfully.digibookygroup3.api.dtos.CreateLibrarianDTO;
import com.switchfully.digibookygroup3.api.dtos.CreateMemberDTO;
import com.switchfully.digibookygroup3.api.dtos.LibrarianDTO;
import com.switchfully.digibookygroup3.api.dtos.MemberDTO;
import com.switchfully.digibookygroup3.domain.user.Feature;
import com.switchfully.digibookygroup3.domain.user.Member;
import com.switchfully.digibookygroup3.domain.user.User;
import com.switchfully.digibookygroup3.service.SecurityService;
import com.switchfully.digibookygroup3.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Members")
@RestController
@RequestMapping(path = "/members")
public class MemberController {

    private final UserService userService;
    private final SecurityService securityService;

    public MemberController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDTO createMember(@Valid @RequestBody CreateMemberDTO dto) {
        Member created = userService.addMember(dto);
        return userService.toMemberDTO(created);
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<MemberDTO> getAllMembers(@RequestHeader(value = "Authorization", required = false) String authorization) {
        securityService.validateAuthorization(authorization, Feature.VIEW_MEMBERS);
        return userService.getMemberDTOs();
    }

    @PostMapping(path = "librarians", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public LibrarianDTO createLibrarian(@RequestHeader(value = "Authorization", required = false) String authorization, @Valid @RequestBody CreateLibrarianDTO dto) {
        securityService.validateAuthorization(authorization, Feature.REGISTER_LIBRARIAN);
        return userService.addLibrarian(dto);
    }
}
