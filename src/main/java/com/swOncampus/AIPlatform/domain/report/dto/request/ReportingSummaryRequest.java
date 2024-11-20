package com.swOncampus.AIPlatform.domain.report.dto.request;

import lombok.Builder;

@Builder
public record ReportingSummaryRequest(
    String title,
    String content
) {

}
