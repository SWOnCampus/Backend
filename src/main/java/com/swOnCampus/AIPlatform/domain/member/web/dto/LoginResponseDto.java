package com.swOnCampus.AIPlatform.domain.member.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

public class LoginResponseDto {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginResponse {
        private String name;
        private String token;
    }
}
