package com.swOnCampus.AIPlatform.domain.report.web.controller;

import com.swOnCampus.AIPlatform.domain.report.service.ReportService;
import com.swOnCampus.AIPlatform.domain.report.web.dto.ReportingSummaryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/report")
public class ReportController {

    private final ReportService reportService;

    @GetMapping()
    public ResponseEntity<byte[]> createReportingSummaryPdf(Model model) {
        model.addAttribute("title", "리포팅 요약");
        model.addAttribute("content", "내용");

        ReportingSummaryRequest request = ReportingSummaryRequest.builder()
                .title(model.getAttribute("title").toString())
                .content(model.getAttribute("content").toString())
                .build();

        byte[] pdfData = reportService.createReportingSummaryPdf(request);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfData);
    }
}
