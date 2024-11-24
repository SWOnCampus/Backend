package com.swOnCampus.AIPlatform.domain.mypage.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "ProfileEditRequestDto", description = "프로필 정보 수정 API 요청 DTO")
public class ProfileEditRequestDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProfileEditRequest {

        @Schema(description = "수정할 이메일을 입력해주세요.", example = "example@naver.com")
        private String email;

        @Schema(description = "수정할 이름을 입력해주세요.", example = "김예시")
        private String name;

        @Schema(description = "수정할 전화번호을 입력해주세요.", example = "010-1234-5678")
        private String phone;
    }
}
