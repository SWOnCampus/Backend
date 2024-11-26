package com.swOnCampus.AIPlatform.domain.mypage.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ProfileEditResponseDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProfileEditResponse {
        private long id;
        private String name;
        private String phone;
        private String email;
    }
}
