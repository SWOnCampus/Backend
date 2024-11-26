package com.swOnCampus.AIPlatform.domain.consulting.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ConsultingAllRequestDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ConsultingAllRequest {
        private String industry;
        private String company_size;
        private String pain_point;
    }
}
