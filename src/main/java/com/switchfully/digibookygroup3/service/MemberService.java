package com.switchfully.digibookygroup3.service;

import com.switchfully.digibookygroup3.api.MemberMapper;
import com.switchfully.digibookygroup3.api.dtos.CreateMemberDTO;
import com.switchfully.digibookygroup3.api.dtos.MemberDTO;
import com.switchfully.digibookygroup3.domain.member.Member;
import com.switchfully.digibookygroup3.domain.member.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    public MemberService(MemberRepository memberRepository, MemberMapper memberMapper) {

        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
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

    public MemberDTO toMemberDTO(Member member) {
        return memberMapper.mapMemberToMemberDTO(member);
    }

    public List<MemberDTO> getMemberDTOs() {
        List<Member> members = memberRepository.getAllMembers();
        return memberMapper.mapMemberToMemberDTO(members);
    }
}
