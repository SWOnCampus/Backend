package com.swOnCampus.AIPlatform.domain.member.service;


public interface MemberService {
    Boolean isExistEmail(String email);
    Boolean isExistCorporation(String corporation);
}
