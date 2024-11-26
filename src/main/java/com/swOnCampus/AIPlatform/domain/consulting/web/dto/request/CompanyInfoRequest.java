package com.swOnCampus.AIPlatform.domain.consulting.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CompanyInfoRequest(
    @Schema(description = "회사명을 입력해주세요.", example = "company")
    @NotBlank(message = "회사명을 입력해주세요.")
    String name,
    @Schema(description = "기업규모를 입력해주세요.", example = "Small")
    @NotBlank(message = "기업규모를 입력해주세요.")
    String companySize,
    @Schema(description = "산업 분야를 입력해주세요.", example = "Retail")
    @NotBlank(message = "산업 분야를 입력해주세요.")
    String industry,
    @Schema(description = "Pain point를 입력해주세요.", example = "String")
    @NotBlank(message = "Pain Point를 입력해주세요.")
    String painPoint
) {

}
