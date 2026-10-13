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

    public Member addMember(CreateMemberDTO dto) {
        Member member = new Member(
                checkINSSUnique(dto.getINSS()),
                dto.getLastname(),
                dto.getFirstname(),
                checkEmailUnique(dto.getEmail()),
                dto.getStreetName(),
                dto.getStreetNumber(),
                dto.getPostalCode(),
                dto.getCity()
        );
        memberRepository.addMember(member);
        return member;
    }

    private String checkEmailUnique(String email) {
        if(memberRepository.isEmailPresent(email)){
            throw  new RuntimeException();
        }
        return email;
    }

    private String checkINSSUnique(String inss) {
        if(memberRepository.isINSSPresent(inss)){
            throw  new RuntimeException();
        }
        return inss;
    }
}
