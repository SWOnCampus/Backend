package com.swOnCampus.AIPlatform.domain.consulting.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swOnCampus.AIPlatform.domain.consulting.entity.Consulting;
import com.swOnCampus.AIPlatform.domain.consulting.repository.ConsultingRepository;
import com.swOnCampus.AIPlatform.domain.consulting.web.dto.request.ConsultingAllRequestDto;
import com.swOnCampus.AIPlatform.domain.consulting.web.dto.response.ConsultingAllResponseDto;
import com.swOnCampus.AIPlatform.domain.member.entity.Member;
import com.swOnCampus.AIPlatform.domain.member.repository.MemberRepository;
import com.swOnCampus.AIPlatform.domain.report.service.ReportServiceImpl;
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
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsultingAllServiceImpl implements ConsultingAllService {
    private final MemberRepository memberRepository;
    private final ConsultingRepository consultingRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ReportServiceImpl reportService;

    @Value(value = "${ai.api.url}")
    private String apiUrl;


    @Override
    public ConsultingAllResponseDto.ConsultingAllResponse getConsultingAll(long memberId, ConsultingAllRequestDto.ConsultingAllRequest request) {

        String baseUrl = apiUrl + "/api/consulting";

        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("summary", "False")
                .queryParam("test", "False")
                .build()
                .toUri();

        log.info("uri: {}", uri);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");

        try {
            HttpEntity<ConsultingAllRequestDto.ConsultingAllRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(uri, entity, String.class);

            String responseBody = response.getBody();
            log.info("response: {}", responseBody);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("컨설팅 생성 오류 : " + response.getStatusCode());
            }

            JsonNode rootNode = objectMapper.readTree(responseBody);
            if (!rootNode.has("result") || rootNode.get("result").isNull()) {
                throw new RuntimeException("컨설팅 생성에 오류가 발생하였습니다.");
            }
            String result = rootNode.get("result").asText();

            Optional<Member> foundMember = memberRepository.findById(memberId);
            if (!foundMember.isPresent()) {
                throw new RuntimeException("존재하지 않는 멤버입니다.");
            }

            Member found = foundMember.get();

            Consulting newAllConsulting = Consulting.builder()
                    .consultingAll(result)
                    .result(result)
                    .build();

            found.addConsulting(newAllConsulting);

            // 컨설팅 저장
            consultingRepository.save(newAllConsulting);

            return ConsultingAllResponseDto.ConsultingAllResponse.builder()
                    .result(result)
                    .pdf(createPDF(result))
                    .build();

        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("전체 컨설팅 생성에 실패하였습니다.");
        }
    }

    public String createPDF(String result){
        String summaryHtml = result.replace("\n", "<br />");
        String renderedMarkdown = reportService.renderMarkdown(summaryHtml);

        Map<String, Object> contextVariables = Map.of(
                "content", renderedMarkdown
        );
        String html = reportService.createHtmlFromTemplate(contextVariables);
        byte[] pdfBytes = reportService.convertHtmlToPdf(html);

        return Base64.getEncoder().encodeToString(pdfBytes);
    }
}
