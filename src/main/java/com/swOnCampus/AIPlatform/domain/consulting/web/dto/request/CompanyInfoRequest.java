package com.swOnCampus.AIPlatform.domain.consulting.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CompanyInfoRequest(
    @NotBlank(message="회사명을 입력해주세요.")
    String name,
    @NotBlank(message="기업규모를 입력해주세요.")
    String companySize,
    @NotBlank(message="산업 분야를 입력해주세요.")
    String industry,
    @NotBlank(message="Pain Point를 입력해주세요.")
    String painPoint
) {
}
