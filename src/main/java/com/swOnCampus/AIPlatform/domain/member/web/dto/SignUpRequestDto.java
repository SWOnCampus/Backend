package com.swOnCampus.AIPlatform.domain.member.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "SignUpRequestDto", description = "회원가입 API 요청 DTO")
public class SignUpRequestDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignupRequest{

        @NotNull(message = "이메일을 입력해주세요.")
        @Schema(description = "회원가입을 할 이메일을 입력해주세요.", example = "example@naver.com")
        private String email;
        @Pattern(
                regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=-]).{8,}$",
                message = "비밀번호는 특수문자, 영어, 숫자를 포함하여 8자리 이상이어야 합니다."
        )

        @NotNull(message = "비밀번호를 입력해주세요.")
        @Schema(description = "비밀번호를 입력해주세요.", example = "examplePW@890")
        private String password;

        @NotNull(message = "이름을 입력해주세요.")
        @Schema(description = "이름을 입력해주세요.", example = "김예시")
        private String name;

        @NotNull(message = "전화번호를 입력해주세요.")
        @Schema(description = "전화번호를 입력해주세요.", example = "010-1234-5678")
        private String phone;

        @NotNull(message = "사업자번호를 입력해주세요.")
        @Schema(description = "사업자번호를 입력해주세요.", example = "123-45-67890")
        private String businessNum;

        @Schema(description = "가입 경로를 입력해주세요. 필수값은 아닙니다.", example = "인스타")
        private String signupRoute;

        @NotNull(message = "소속된 회사를 입력해주세요.")
        @Schema(description = "소속된 회사를 입력해주세요.", example = "삼성전자")
        private String corporation;
    }
}
