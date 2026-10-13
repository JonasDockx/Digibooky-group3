package com.switchfully.digibookygroup3.service;

import com.switchfully.digibookygroup3.api.dtos.CreateMemberDTO;
import com.switchfully.digibookygroup3.domain.member.Member;
import com.switchfully.digibookygroup3.domain.member.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member addMember(CreateMemberDTO createMemberDTO) {

    }
}
