package com.swOnCampus.AIPlatform.domain.mypage.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ProfileEditRequestDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProfileEditRequest {
        private String email;
        private String name;
        private String phone;
    }
}
