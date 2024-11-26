package com.swOnCampus.AIPlatform.domain.consulting.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swOnCampus.AIPlatform.domain.consulting.entity.Consulting;
import com.swOnCampus.AIPlatform.domain.consulting.web.dto.request.ConsultingAllRequestDto;
import com.swOnCampus.AIPlatform.domain.consulting.web.dto.response.ConsultingAllResponseDto;
import com.swOnCampus.AIPlatform.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsultingAllServiceImpl implements ConsultingAllService {
    private final MemberRepository memberRepository;
    private final RestTemplate restTemplate;

    @Value(value = "${ai.api.url}")
    private String apiUrl;

    @Override
    public ConsultingAllResponseDto.ConsultingAllResponse getConsultingAll(long memberId,ConsultingAllRequestDto.ConsultingAllRequest request) {

        String baseUrl = apiUrl + "/api/consulting";

        String urlWithParams = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("summary", "False")
                .queryParam("test", "True")
                .toUriString();

        log.info("uri: {}", urlWithParams);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");

        try{
            HttpEntity<ConsultingAllRequestDto.ConsultingAllRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(new URI(urlWithParams), entity, String.class);

            String responseBody = response.getBody();

            log.info("response: {}", responseBody);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Unexpected status code: " + response.getStatusCode());
            }

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);

            String result = rootNode.get("result").asText();


            Consulting newAllConsulting = Consulting.builder()
                    .consultingAll(result)
                    .build();


            memberRepository.findById(memberId).ifPresent(member -> {
                member.addConsulting(newAllConsulting);
            });

            return ConsultingAllResponseDto.ConsultingAllResponse.builder()
                    .result(result)
                    .build();

        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("전체 컨설팅 생성에 실패하였습니다.");
        }
    }
}
