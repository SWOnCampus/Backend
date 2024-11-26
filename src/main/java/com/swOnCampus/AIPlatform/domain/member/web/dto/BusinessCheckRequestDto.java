package com.swOnCampus.AIPlatform.domain.member.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "BusinessCheckRequestDto", description = "사업자번호 진위여부 체크 API 요청 DTO")
public class BusinessCheckRequestDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BusinessCheckRequest {
        @NotNull(message = "사업자번호를 입력해주세요.")
        @Schema(description = "'-'의 존재 여부는 상관 없습니다.", example = "123-45-67890")
        private String businessNumber;
    }
}
