package com.swOnCampus.AIPlatform.domain.member.service;

import com.swOnCampus.AIPlatform.domain.member.entity.Member;
import com.swOnCampus.AIPlatform.domain.member.repository.MemberRepository;
import com.swOnCampus.AIPlatform.domain.member.web.dto.EmailCheckRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    MemberRepository memberRepository;


    // Email 중복 검사 함수
    @Override
    public Boolean isExistEmail(String email) {
        boolean findMember = memberRepository.existsByEmail(email);

        if(findMember){
            return true;
        }

        return false;
    }


}