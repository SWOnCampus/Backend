package com.swOnCampus.AIPlatform.domain.member.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class BusinessCheckResponseDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class BusinessCheckResponse {
        private String businessNum;
    }
}
