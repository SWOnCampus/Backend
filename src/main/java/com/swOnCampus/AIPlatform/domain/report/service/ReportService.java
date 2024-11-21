package com.swOnCampus.AIPlatform.domain.report.service;

import com.swOnCampus.AIPlatform.domain.report.web.dto.ReportingSummaryRequest;

public interface ReportService {

    /**
     * 리포팅 요약된 내용을 html로 변환 하여 pdf를 생성하는 메서드
     *
     * @param request : 리포팅 요약 데이터를 담은 request 객체
     * @return : 생성된 PDF 파일의 바이트 배열
     */
    byte[] createReportingSummaryPdf(ReportingSummaryRequest request);
}
