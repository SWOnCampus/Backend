package com.swOnCampus.AIPlatform.domain.mypage.web.controller;

import com.swOnCampus.AIPlatform.domain.member.entity.Member;
import com.swOnCampus.AIPlatform.domain.member.web.dto.EmailCheckResponseDto;
import com.swOnCampus.AIPlatform.domain.mypage.service.MypageService;
import com.swOnCampus.AIPlatform.domain.mypage.web.dto.MyProfileResponseDto;
import com.swOnCampus.AIPlatform.domain.mypage.web.dto.ProfileEditRequestDto;
import com.swOnCampus.AIPlatform.domain.mypage.web.dto.ProfileEditResponseDto;
import com.swOnCampus.AIPlatform.global.annotation.LoginMember;
import com.swOnCampus.AIPlatform.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "마이페이지 API", description = "마이페이지 관련 API")
public class MypageController {
    private final MypageService mypageService;

    @Operation(summary = "프로필 조회 API 요청", description = "마이페이지 프로필 정보 조회 API 요청")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "COMMON200",
                    description = "요청 성공",
                    content = {
                            @Content(
                                    schema = @Schema(
                                            implementation = MyProfileResponseDto.MyProfileResponse.class
                                    )
                            )
                    }
            )
    })
    @GetMapping("/get-my-profile")
    public ResponseEntity<ApiResponse<MyProfileResponseDto.MyProfileResponse>> getMyProfile(@LoginMember Member member) {
        log.info("memberId:{}", member.getMemberId());
        MyProfileResponseDto.MyProfileResponse responseDto = mypageService.getMyProfile(member.getMemberId());

        ApiResponse<MyProfileResponseDto.MyProfileResponse> response = ApiResponse.createSuccess(HttpStatus.OK.value(), responseDto, "프로필 조회에 성공하였습니다.");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "프로필 수정 API 요청", description = "마이페이지 프로필 정보 수정 API 요청")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "COMMON200",
                    description = "요청 성공",
                    content = {
                            @Content(
                                    schema = @Schema(
                                            implementation = ProfileEditResponseDto.ProfileEditResponse.class
                                    )
                            )
                    }
            )
    })
    @PutMapping("/edit-my-profile")
    public ResponseEntity<ApiResponse<ProfileEditResponseDto.ProfileEditResponse>> editMyProfile(@LoginMember Member member, @Valid @RequestBody ProfileEditRequestDto.ProfileEditRequest request) {
        ProfileEditResponseDto.ProfileEditResponse responseDto = mypageService.editProfile(member.getMemberId(), request);

        ApiResponse<ProfileEditResponseDto.ProfileEditResponse> response = ApiResponse.createSuccess(HttpStatus.OK.value(), responseDto, "프로필 정보 수정에 성공하였습니다.");
        return ResponseEntity.ok(response);
    }
}
