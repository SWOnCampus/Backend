package com.swOnCampus.AIPlatform.domain.consulting.service;

import com.swOnCampus.AIPlatform.domain.consulting.web.dto.ConsultingSave;
import com.swOnCampus.AIPlatform.domain.consulting.web.dto.request.ConsultingRequest;

public interface ConsultingService {

    ConsultingSave getConsultingResult(ConsultingRequest request);
}
