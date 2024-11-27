package com.swOnCampus.AIPlatform.domain.consulting.web.dto.request;

public record ConsultingRequest(
    String industry,
    String company_size,
    String pain_point
) {

}