package com.switchfully.digibookygroup3.service;

import com.switchfully.digibookygroup3.api.MemberMapper;
import com.switchfully.digibookygroup3.api.dtos.CreateMemberDTO;
import com.switchfully.digibookygroup3.api.dtos.MemberDTO;
import com.switchfully.digibookygroup3.domain.user.Member;
import com.switchfully.digibookygroup3.domain.user.UserRepository;
import com.switchfully.digibookygroup3.exceptions.UserAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private static final Logger logger = LoggerFactory.getLogger(MemberService.class);

    private final UserRepository userRepository;
    private final MemberMapper memberMapper;

    public MemberService(UserRepository userRepository, MemberMapper memberMapper) {

        this.userRepository = userRepository;
        this.memberMapper = memberMapper;
    }

    public Member addMember(CreateMemberDTO dto) {
        Member member = new Member(
                checkINSSUnique(dto.getInss()),
                dto.getLastname(),
                dto.getFirstname(),
                checkEmailUnique(dto.getEmail()),
                dto.getStreetName(),
                dto.getStreetNumber(),
                dto.getPostalCode(),
                dto.getCity()
        );
        userRepository.addMember(member);
        logger.info("Member created with id {}", member.getId());
        return member;
    }

    private String checkEmailUnique(String email) {
        if (userRepository.isEmailPresent(email.trim().toLowerCase())) {
            logger.error("Duplicate e-mail address");
            throw new UserAlreadyExistsException();
        }
        logger.info("E-mail address {} is valid and not in use yet", email);
        return email;
    }

    private String checkINSSUnique(String inss) {
        if (userRepository.isINSSPresent(inss.trim())) {
            logger.error("Duplicate INSS number");
            throw new UserAlreadyExistsException();
        }
        logger.info("INSS number {} is valid and not in use yet", inss);
        return inss;
    }

    public MemberDTO toMemberDTO(Member member) {
        logger.info("Turn member with id {} into a MemberDTO", member.getId());
        return memberMapper.mapMemberToMemberDTO(member);
    }

    public List<MemberDTO> getMemberDTOs() {
        List<Member> members = userRepository.getAllMembers();
        logger.info("Fetching all members.");
        return memberMapper.mapMemberToMemberDTO(members);
    }
}
