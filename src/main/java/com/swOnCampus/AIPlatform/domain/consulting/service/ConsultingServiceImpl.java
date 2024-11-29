package com.swOnCampus.AIPlatform.domain.consulting.service;

import com.swOnCampus.AIPlatform.domain.consulting.web.dto.ConsultingSave;
import com.swOnCampus.AIPlatform.domain.consulting.web.dto.request.ConsultingRequest;
import com.swOnCampus.AIPlatform.domain.consulting.web.dto.response.ConsultingAiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class ConsultingServiceImpl implements ConsultingService {

    private final RestTemplate restTemplate;

    @Value(value = "${ai.api.url}")
    private String aiApiUrl;

    @Override
    public ConsultingSave getConsultingResult(ConsultingRequest request) {
        ConsultingSave response = new ConsultingSave(
            sendRequestToAi(request, false).result(),
            sendRequestToAi(request, true).result()
        );

        return response;
    }

    private ConsultingAiResponse sendRequestToAi(ConsultingRequest jsonRequest, boolean summary) {
        String url = UriComponentsBuilder.fromHttpUrl(aiApiUrl + "/api/consulting")
            .queryParam("summary", summary)
            .queryParam("test", false)
            .toUriString();

        ConsultingAiResponse response = restTemplate.postForObject(url, jsonRequest,
            ConsultingAiResponse.class);

        return response;
    }
}
