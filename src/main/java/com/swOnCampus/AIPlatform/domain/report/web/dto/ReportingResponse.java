package com.swOnCampus.AIPlatform.domain.report.web.dto;

import lombok.Builder;

@Builder
public record ReportingResponse(
    String summary,
    String pdf
) {

}