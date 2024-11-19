package com.swOnCampus.AIPlatform.domain.member.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class SignUpResponseDto {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignUpResponse {
        private String name;
        private String token;
    }
}
