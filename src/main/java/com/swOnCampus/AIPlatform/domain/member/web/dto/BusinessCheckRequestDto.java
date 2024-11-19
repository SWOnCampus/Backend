package com.swOnCampus.AIPlatform.domain.member.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class BusinessCheckRequestDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BusinessCheckRequest {
        private String businessNumber;
    }
}
