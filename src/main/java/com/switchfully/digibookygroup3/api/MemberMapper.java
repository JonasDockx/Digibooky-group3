package com.switchfully.digibookygroup3.api;

import com.switchfully.digibookygroup3.api.dtos.CreateMemberDTO;
import com.switchfully.digibookygroup3.api.dtos.MemberDTO;
import com.switchfully.digibookygroup3.domain.user.Member;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberMapper {

    public Member mapDTOToMember(CreateMemberDTO createMemberDTO) {
        return new Member(
                createMemberDTO.getInss(),
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
                member.getInss(),
                member.getLastname(),
                member.getFirstname(),
                member.getEmail(),
                member.getStreetName(),
                member.getStreetNumber(),
                member.getPostalCode(),
                member.getCity(),
                member.getUserType());
    }

    public List<MemberDTO> mapMemberToMemberDTO(List<Member> members) {
        return members.stream()
                .map(this::mapMemberToMemberDTO)
                .toList();
    }
}
