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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "기업정보 입력에 따른 컨설팅 결과 API", description = "기업정보 입력/컨설팅 요약 결과 조회 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/consulting")
public class ConsultingController {

    private final CompanyService companyService;
    private final ConsultingAllService consultingAllService;

    @Operation(summary = "기업정보 입력 API 요청", description = "AI 컨설팅에 필요한 기업정보 입력 API 요청")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "COMMON200",
            description = "요청 성공",
            content = {
                @Content(
                    schema = @Schema(
                        implementation = CompanyInfoRequest.class
                    )
                )
            }
        )
    })
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


    @Operation(summary = "상세 컨설팅 생성 API 요청", description = "요약본이 아닌 전체 컨설팅 정보 생성")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "COMMON200",
                    description = "요청 성공",
                    content = {
                            @Content(
                                    schema = @Schema(
                                            implementation = ConsultingAllResponseDto.ConsultingAllResponse.class
                                    )
                            )
                    }
            )
    })
    // 컨설팅 결과 전체 상세 보기 (요약 x)
    @PostMapping("/all")
    public ResponseEntity<ApiResponse<ConsultingAllResponseDto.ConsultingAllResponse>> getAllConsulting(@LoginMember Member member, @RequestBody ConsultingAllRequestDto.ConsultingAllRequest request){
        ConsultingAllResponseDto.ConsultingAllResponse responseDto = consultingAllService.getConsultingAll(member.getMemberId(), request);

        ApiResponse<ConsultingAllResponseDto.ConsultingAllResponse> response = ApiResponse.createSuccess(HttpStatus.OK.value(), responseDto, "컨설팅 세부 정보 불러오기를 완료하였습니다.");
        return ResponseEntity.ok(response);
    }
}
