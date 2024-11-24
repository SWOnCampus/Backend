package com.swOnCampus.AIPlatform.domain.consulting.web.controller;

import com.swOnCampus.AIPlatform.domain.consulting.service.CompanyService;
import com.swOnCampus.AIPlatform.domain.consulting.web.dto.request.CompanyInfoRequest;
import com.swOnCampus.AIPlatform.domain.consulting.web.dto.response.ConsultingResponse;
import com.swOnCampus.AIPlatform.domain.member.entity.Member;
import com.swOnCampus.AIPlatform.global.annotation.LoginMember;
import com.swOnCampus.AIPlatform.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/consulting")
public class ConsultingController {

    private final CompanyService companyService;

    @PostMapping()
    public ResponseEntity<ApiResponse<?>> createConsulting(
        @LoginMember Member member,
        @RequestBody CompanyInfoRequest companyInfoRequest,
        @RequestParam(required = false) Long companyId // 채팅방 id
    ) {
        ConsultingResponse result = companyService.createOrGetConsulting(
            member.getMemberId(), companyInfoRequest, companyId);

        ApiResponse<ConsultingResponse> response = ApiResponse.createSuccess(HttpStatus.OK.value(),
            result, "컨설팅 결과가 생성되었습니다.");

        return ResponseEntity.ok(response);
    }
}
