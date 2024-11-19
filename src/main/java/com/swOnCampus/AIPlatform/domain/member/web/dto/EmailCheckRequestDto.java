package com.swOnCampus.AIPlatform.domain.member.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class EmailCheckRequestDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmailCheckRequest {
        private String email;
    }
}
