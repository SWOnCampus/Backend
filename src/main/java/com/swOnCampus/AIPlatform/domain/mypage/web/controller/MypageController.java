package com.swOnCampus.AIPlatform.domain.mypage.web.controller;

import com.swOnCampus.AIPlatform.domain.member.entity.Member;
import com.swOnCampus.AIPlatform.domain.mypage.service.MypageService;
import com.swOnCampus.AIPlatform.domain.mypage.web.dto.MyProfileResponseDto;
import com.swOnCampus.AIPlatform.domain.mypage.web.dto.ProfileEditRequestDto;
import com.swOnCampus.AIPlatform.domain.mypage.web.dto.ProfileEditResponseDto;
import com.swOnCampus.AIPlatform.global.annotation.LoginMember;
import com.swOnCampus.AIPlatform.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
public class MypageController {
    private final MypageService mypageService;

    @GetMapping("/get-my-profile")
    public ResponseEntity<ApiResponse<MyProfileResponseDto.MyProfileResponse>> getMyProfile(@LoginMember Member member) {
        log.info("memberId:{}", member.getMemberId());
        MyProfileResponseDto.MyProfileResponse responseDto = mypageService.getMyProfile(member.getMemberId());

        ApiResponse<MyProfileResponseDto.MyProfileResponse> response = ApiResponse.createSuccess(HttpStatus.OK.value(), responseDto, "프로필 조회에 성공하였습니다.");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/edit-my-profile")
    public ResponseEntity<ApiResponse<ProfileEditResponseDto.ProfileEditResponse>> editMyProfile(@LoginMember Member member, @Valid @RequestBody ProfileEditRequestDto.ProfileEditRequest request) {
        ProfileEditResponseDto.ProfileEditResponse responseDto = mypageService.editProfile(member.getMemberId(), request);

        ApiResponse<ProfileEditResponseDto.ProfileEditResponse> response = ApiResponse.createSuccess(HttpStatus.OK.value(), responseDto, "프로필 정보 수정에 성공하였습니다.");
        return ResponseEntity.ok(response);
    }
}
