package com.swOnCampus.AIPlatform.domain.consulting.service;

import com.swOnCampus.AIPlatform.domain.consulting.web.dto.request.CompanyInfoRequest;
import com.swOnCampus.AIPlatform.domain.consulting.web.dto.response.ConsultingResponse;

public interface CompanyService {

    ConsultingResponse saveCompanyInfo(
        Long memberId,
        CompanyInfoRequest companyInfoRequest,
        boolean summary
    );
}
