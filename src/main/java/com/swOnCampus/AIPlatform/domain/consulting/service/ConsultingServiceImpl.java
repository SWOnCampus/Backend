package com.swOnCampus.AIPlatform.domain.consulting.service;

import com.swOnCampus.AIPlatform.domain.consulting.web.dto.request.ConsultingRequest;
import com.swOnCampus.AIPlatform.domain.consulting.web.dto.response.ConsultingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class ConsultingServiceImpl implements ConsultingService {

    private final RestTemplate restTemplate;

    @Value("${ai.api.url}")
    private String aiApiUrl;

    @Override
    public ConsultingResponse sendToAi(ConsultingRequest request, boolean summary) {
        String url = UriComponentsBuilder.fromHttpUrl(aiApiUrl + "/api/consulting")
            .queryParam("summary", summary)
            .toUriString();

        return restTemplate.postForObject(url, request, ConsultingResponse.class);
    }
}
