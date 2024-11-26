package com.swOnCampus.AIPlatform.domain.mypage.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class MyProfileResponseDto {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyProfileResponse {
        private String email;
        private String name;
        private String phone;
        private String corporationNum;
        private List<String> reports;
    }
}
