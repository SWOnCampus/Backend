package com.swOncampus.AIPlatform.domain.report.controller;

import com.swOncampus.AIPlatform.domain.report.dto.request.ReportingSummaryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/report")
public class ReportController {

    @GetMapping()
    public void createReportingSummaryPdf(Model model) {
        model.addAttribute("title", "리포팅 요약");
        model.addAttribute("content", "내용");

        ReportingSummaryRequest request = ReportingSummaryRequest.builder()
            .title(model.getAttribute("title").toString())
            .content(model.getAttribute("content").toString())
            .build();
    }
}
