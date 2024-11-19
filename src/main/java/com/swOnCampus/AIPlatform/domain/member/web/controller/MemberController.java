package com.swOnCampus.AIPlatform.domain.member.web.controller;

import com.swOnCampus.AIPlatform.domain.member.service.MemberService;
import com.swOnCampus.AIPlatform.domain.member.web.dto.EmailCheckRequestDto;
import com.swOnCampus.AIPlatform.domain.member.web.dto.EmailCheckResponseDto;
import com.swOnCampus.AIPlatform.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;

    // 이메일 인증
    @GetMapping("/email-check")
    public ResponseEntity<ApiResponse<?>> emailCheck(@Valid @RequestBody EmailCheckRequestDto.EmailCheckRequest request){
        if(memberService.isExistEmail(request.getEmail())){

            ApiResponse<?> failResponse = ApiResponse.createFailWithoutData(HttpStatus.BAD_REQUEST.value(), "이미 존재하는 이메일입니다.");
            return ResponseEntity.badRequest().body(failResponse);
        }

        EmailCheckResponseDto.EmailCheckResponse responseDto = EmailCheckResponseDto.EmailCheckResponse.builder()
                .email(request.getEmail())
                .build();

        ApiResponse<EmailCheckResponseDto.EmailCheckResponse> successResponse = ApiResponse.createSuccess(HttpStatus.OK.value(), responseDto, "이메일 인증이 성공적으로 완료되었습니다.");
        return ResponseEntity.ok(successResponse);

    }
}
