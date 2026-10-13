package com.switchfully.digibookygroup3.api;

import com.switchfully.digibookygroup3.api.dtos.CreateMemberDTO;
import com.switchfully.digibookygroup3.api.dtos.MemberDTO;
import com.switchfully.digibookygroup3.domain.member.Member;
import com.switchfully.digibookygroup3.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Members")
@RestController
@RequestMapping(path = "/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper memberMapper;

    public MemberController(MemberService memberService, MemberMapper memberMapper) {
        this.memberService = memberService;
        this.memberMapper = memberMapper;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDTO createMember(@Valid @RequestBody CreateMemberDTO dto) {
        Member created = memberService.addMember(dto);
        return memberService.toMemberDTO(created);
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<MemberDTO> getAllMembers() {
        return memberService.getMemberDTOs();
    }
}
