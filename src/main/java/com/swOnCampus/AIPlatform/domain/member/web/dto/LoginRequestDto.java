package com.swOnCampus.AIPlatform.domain.member.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "LoginRequestDto", description = "로그인 API 요청 DTO")
public class LoginRequestDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginRequest {

        @NotNull(message = "이메일을 입력해주세요.")
        @Schema(description = "로그인 할 이메일을 입력해주세요.", example = "example@naver.com")
        private String email;

        @NotNull(message = "비밀번호를 입력해주세요.")
        @Schema(description = "로그인 할 비밀번호를 입력해주세요.", example = "examplePw@890")
        private String password;
    }
}
