package com.swOnCampus.AIPlatform.domain.consulting.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ConsultingAllResponseDto {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ConsultingAllResponse {
        private String result;
        private String pdf;
    }
}
