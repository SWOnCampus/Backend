package com.swOnCampus.AIPlatform.domain.consulting.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ConsultingRequest(
    @JsonProperty("industry") String industry,
    @JsonProperty("company_size") String companySize,
    @JsonProperty("pain_point") String painPoint
) {

}