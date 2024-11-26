package com.swOnCampus.AIPlatform.domain.consulting.web.controller;

import com.swOnCampus.AIPlatform.domain.consulting.service.CompanyService;
import com.swOnCampus.AIPlatform.domain.consulting.service.ConsultingAllService;
import com.swOnCampus.AIPlatform.domain.consulting.web.dto.request.CompanyInfoRequest;
import com.swOnCampus.AIPlatform.domain.consulting.web.dto.request.ConsultingAllRequestDto;
import com.swOnCampus.AIPlatform.domain.consulting.web.dto.response.ConsultingAllResponseDto;
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
    private final ConsultingAllService consultingAllService;

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

    // 컨설팅 결과 전체 상세 보기 (요약 x)
    @PostMapping("/all")
    public ResponseEntity<ApiResponse<ConsultingAllResponseDto.ConsultingAllResponse>> getAllConsulting(@LoginMember Member member, @RequestBody ConsultingAllRequestDto.ConsultingAllRequest request){
        ConsultingAllResponseDto.ConsultingAllResponse responseDto = consultingAllService.getConsultingAll(member.getMemberId(), request);

        ApiResponse<ConsultingAllResponseDto.ConsultingAllResponse> response = ApiResponse.createSuccess(HttpStatus.OK.value(), responseDto, "컨설팅 세부 정보 불러오기를 완료하였습니다.");
        return ResponseEntity.ok(response);
    }
}
