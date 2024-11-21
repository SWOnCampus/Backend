package com.swOnCampus.AIPlatform.domain.mypage.service;

import com.swOnCampus.AIPlatform.domain.mypage.web.dto.MyProfileResponseDto;
import com.swOnCampus.AIPlatform.domain.mypage.web.dto.ProfileEditRequestDto;
import com.swOnCampus.AIPlatform.domain.mypage.web.dto.ProfileEditResponseDto;

public interface MypageService {
    MyProfileResponseDto.MyProfileResponse getMyProfile(long memberId);
    ProfileEditResponseDto.ProfileEditResponse editProfile(long memberId, ProfileEditRequestDto.ProfileEditRequest request);
}
