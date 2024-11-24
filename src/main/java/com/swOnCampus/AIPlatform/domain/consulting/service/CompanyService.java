package com.swOnCampus.AIPlatform.domain.consulting.service;

import com.swOnCampus.AIPlatform.domain.consulting.web.dto.request.CompanyInfoRequest;
import com.swOnCampus.AIPlatform.domain.consulting.web.dto.response.ConsultingResponse;

public interface CompanyService {

    ConsultingResponse createOrGetConsulting(
        Long memberId,
        CompanyInfoRequest companyInfoRequest,
        Long chatRoomId
    );
}
