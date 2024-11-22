package com.swOnCampus.AIPlatform.domain.member.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

public class EmailCheckResponseDto {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmailCheckResponse{
        private String email;
    }
}
