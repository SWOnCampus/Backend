package com.swOnCampus.AIPlatform.domain.report.web.controller;

import com.swOnCampus.AIPlatform.domain.member.entity.Member;
import com.swOnCampus.AIPlatform.domain.report.service.ReportService;
import com.swOnCampus.AIPlatform.domain.report.web.dto.ReportingResponse;
import com.swOnCampus.AIPlatform.global.annotation.LoginMember;
import com.swOnCampus.AIPlatform.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/report")
public class ReportController {

    private final ReportService reportService;

    @GetMapping()
    public ResponseEntity<ApiResponse<?>> createReportingPdf(
        @LoginMember Member member,
        @RequestParam Long companyId // 채팅방 id
    ) {
        ReportingResponse reportingResponse = reportService.createReportingPdf(
            member.getMemberId(), companyId);

        ApiResponse<ReportingResponse> response = ApiResponse.createSuccess(HttpStatus.OK.value(),
            reportingResponse,
            "PDF 파일이 생성되었습니다.");

        return ResponseEntity.ok(response);
    }
}