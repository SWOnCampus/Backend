package com.swOnCampus.AIPlatform.domain.consulting.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record ConsultingResponse(
    @Schema(description = "컨설팅 요약 내용", example = "summary")
    String summary
) {

}
