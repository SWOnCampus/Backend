package com.swOnCampus.AIPlatform.domain.member.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "EmailCheckRequestDto", description = "이메일 중복 여부 확인 API 요청 DTO")
public class EmailCheckRequestDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmailCheckRequest {
        @NotNull(message = "이메일을 입력해주세요.")
        @Schema(description = "중복 여부를 체크할 이메일을 입력해주세요.", example = "example@naver.com")
        private String email;
    }
}
