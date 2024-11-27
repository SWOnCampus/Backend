package com.swOnCampus.AIPlatform.domain.report.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ReportingResponse(
    @Schema(description = "컨설팅 요약 내용", example = "컨설팅 요약")
    String summary,
    @Schema(description = "컨설팅 요약 내용 PDF 파일의 Base64 인코딩 데이터", example = "byte")
    String pdf
) {

}