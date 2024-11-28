package com.swOnCampus.AIPlatform.domain.report.service;

import com.swOnCampus.AIPlatform.domain.consulting.web.dto.request.CompanyInfoRequest;
import com.swOnCampus.AIPlatform.domain.report.web.dto.ReportingResponse;

public interface ReportService {

    ReportingResponse createReportingPdf(Long memberId/*, Long companyId*/,
        CompanyInfoRequest request);
}