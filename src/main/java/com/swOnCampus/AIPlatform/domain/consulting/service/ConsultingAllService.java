package com.swOnCampus.AIPlatform.domain.consulting.service;

import com.swOnCampus.AIPlatform.domain.consulting.web.dto.request.ConsultingAllRequestDto;
import com.swOnCampus.AIPlatform.domain.consulting.web.dto.response.ConsultingAllResponseDto;

public interface ConsultingAllService {
    ConsultingAllResponseDto.ConsultingAllResponse getConsultingAll(long memberId,ConsultingAllRequestDto.ConsultingAllRequest request);
}
