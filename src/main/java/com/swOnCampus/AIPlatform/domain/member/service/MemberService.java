package com.swOnCampus.AIPlatform.domain.member.service;


import com.swOnCampus.AIPlatform.domain.member.web.dto.SignUpRequestDto;
import com.swOnCampus.AIPlatform.domain.member.web.dto.SignUpResponseDto;

public interface MemberService {
    Boolean isExistEmail(String email);
    Boolean isExistCorporation(String corporation);
    SignUpResponseDto.SignUpResponse signUp(SignUpRequestDto.SignupRequest request);


}
