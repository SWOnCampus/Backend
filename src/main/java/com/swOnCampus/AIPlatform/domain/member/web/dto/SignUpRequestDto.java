package com.swOnCampus.AIPlatform.domain.member.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class SignUpRequestDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignupRequest{
        private String email;
        private String password;
        private String name;
        private String phone;
        private String businessNum;
        private String signupRoute;
        private String corporation;
    }
}
