package com.swOnCampus.AIPlatform.domain.report.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ReportingResponse(
    @Schema(description = "컨설팅 요약 내용", example = "컨설팅 요약")
    String summary,
    @Schema(description = "컨설팅 전체 내용 PDF", example = "byte")
    String pdf
) {

}