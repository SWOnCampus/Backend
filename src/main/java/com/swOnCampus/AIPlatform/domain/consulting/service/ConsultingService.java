package com.swOnCampus.AIPlatform.domain.consulting.service;

import com.swOnCampus.AIPlatform.domain.consulting.web.dto.request.ConsultingRequest;
import com.swOnCampus.AIPlatform.domain.consulting.web.dto.response.ConsultingResponse;

public interface ConsultingService {
    ConsultingResponse sendToAi(ConsultingRequest request, boolean summary);
}
