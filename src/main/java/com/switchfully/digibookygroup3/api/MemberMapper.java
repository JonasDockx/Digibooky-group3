package com.switchfully.digibookygroup3.api;

import com.switchfully.digibookygroup3.api.dtos.CreateMemberDTO;
import com.switchfully.digibookygroup3.api.dtos.MemberDTO;
import com.switchfully.digibookygroup3.domain.member.Member;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MemberMapper {

    public Member mapDTOToMember(CreateMemberDTO createMemberDTO) {
        return new Member(
                createMemberDTO.getINSS(),
                createMemberDTO.getLastname(),
                createMemberDTO.getFirstname(),
                createMemberDTO.getEmail(),
                createMemberDTO.getStreetName(),
                createMemberDTO.getStreetNumber(),
                createMemberDTO.getPostalCode(),
                createMemberDTO.getCity());
    }

    public MemberDTO mapMemberToMemberDTO(Member member) {
        return new MemberDTO(
                member.getId(),
                member.getINSS(),
                member.getLastname(),
                member.getFirstname(),
                member.getEmail(),
                member.getStreetName(),
                member.getStreetNumber(),
                member.getPostalCode(),
                member.getCity(),
                member.getMemberType());
    }
}
