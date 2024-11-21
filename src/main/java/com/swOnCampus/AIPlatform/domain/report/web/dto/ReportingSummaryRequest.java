package com.swOnCampus.AIPlatform.domain.report.web.dto;

import lombok.Builder;

@Builder
public record ReportingSummaryRequest(
        String title,
        String content
) {

}