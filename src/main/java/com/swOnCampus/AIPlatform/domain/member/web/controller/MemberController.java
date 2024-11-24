package com.swOnCampus.AIPlatform.domain.member.web.controller;

import com.swOnCampus.AIPlatform.domain.member.service.MemberService;
import com.swOnCampus.AIPlatform.domain.member.web.dto.*;
import com.swOnCampus.AIPlatform.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Tag(name = "회원가입/로그인 API", description = "회원가입/로그인 관련 API")
@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 이메일 인증
    @Operation(summary = "이메일 중복 여부 체크 API 요청", description = "회원가입 시 이메일 중복 여부 체크 API 요청")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "COMMON200",
                    description = "요청 성공",
                    content = {
                            @Content(
                                    schema = @Schema(
                                            implementation = EmailCheckResponseDto.EmailCheckResponse.class
                                    )
                            )
                    }
            )
    })
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

    // 사업자 번호 인증
    @PostMapping("/corporation-num-check")
    public ResponseEntity<ApiResponse<?>> corporationCheck(@Valid @RequestBody BusinessCheckRequestDto.BusinessCheckRequest request){
        if(!memberService.isExistCorporation(request.getBusinessNumber())){

            ApiResponse<?> failResponse = ApiResponse.createFailWithoutData(HttpStatus.NO_CONTENT.value(), "국세청에 등록되지 않은 사업자번호입니다.");

            return ResponseEntity.badRequest().body(failResponse);
        }

        BusinessCheckResponseDto.BusinessCheckResponse responseDto = BusinessCheckResponseDto.BusinessCheckResponse.builder()
                .businessNum(request.getBusinessNumber())
                .build();

        ApiResponse<BusinessCheckResponseDto.BusinessCheckResponse> successResponse = ApiResponse.createSuccess(HttpStatus.OK.value(), responseDto, "사업자번호 인증이 성공적으로 완료되었습니다.");
        return ResponseEntity.ok(successResponse);
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<?>> signup(@Valid @RequestBody SignUpRequestDto.SignupRequest request){
        SignUpResponseDto.SignUpResponse response = memberService.signUp(request);

        ApiResponse<SignUpResponseDto.SignUpResponse> signupResponse = ApiResponse.createSuccess(HttpStatus.OK.value(),response, "회원가입에 성공하였습니다.");
        return ResponseEntity.ok(signupResponse);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody LoginRequestDto.LoginRequest request){
        LoginResponseDto.LoginResponse response = memberService.login(request);

        ApiResponse<LoginResponseDto.LoginResponse> loginResponse = ApiResponse.createSuccess(HttpStatus.OK.value(), response,"로그인에 성공하였습니다.");
        return ResponseEntity.ok(loginResponse);
    }

}
