package com.swOnCampus.AIPlatform.domain.member.web.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class SignUpRequestDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignupRequest{
        private String email;
        @Pattern(
                regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=-]).{8,}$",
                message = "비밀번호는 특수문자, 영어, 숫자를 포함하여 8자리 이상이어야 합니다."
        )
        private String password;
        private String name;
        private String phone;
        private String businessNum;
        private String signupRoute;
        private String corporation;
    }
}
