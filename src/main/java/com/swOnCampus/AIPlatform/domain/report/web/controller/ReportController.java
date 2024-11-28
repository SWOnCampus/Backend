package com.swOnCampus.AIPlatform.domain.report.web.controller;

import com.swOnCampus.AIPlatform.domain.consulting.web.dto.request.CompanyInfoRequest;
import com.swOnCampus.AIPlatform.domain.member.entity.Member;
import com.swOnCampus.AIPlatform.domain.report.service.ReportService;
import com.swOnCampus.AIPlatform.domain.report.web.dto.ReportingResponse;
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
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "컨설팅 PDF 생성 API", description = "컨설팅 결과 PDF 생성 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/report")
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "컨설팅 요약 PDF API 요청", description = "컨설팅 요약 PDF 생성 API 요청")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "COMMON200",
            description = "요청 성공",
            content = {
                @Content(
                    schema = @Schema(
                        implementation = ReportingResponse.class
                    )
                )
            }
        )
    })
    @PostMapping()
    public ResponseEntity<ApiResponse<?>> createReportingPdf(
        @LoginMember Member member,
//        @RequestParam(name="companyId") Long companyId // 채팅방 id
        @RequestBody CompanyInfoRequest companyRequest // 임시코드
    ) {
        ReportingResponse reportingResponse = reportService.createReportingPdf(
            member.getMemberId(), /*companyId*/companyRequest);

        ApiResponse<ReportingResponse> response = ApiResponse.createSuccess(
            HttpStatus.OK.value(),
            reportingResponse,
            "PDF 파일이 생성되었습니다."
        );

        return ResponseEntity.ok(response);
    }
}