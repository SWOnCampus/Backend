package com.swOnCampus.AIPlatform.domain.report.service;

import com.lowagie.text.pdf.BaseFont;
import com.swOnCampus.AIPlatform.domain.consulting.repository.ConsultingRepository;
import com.swOnCampus.AIPlatform.domain.consulting.service.ConsultingService;
import com.swOnCampus.AIPlatform.domain.consulting.web.dto.ConsultingSave;
import com.swOnCampus.AIPlatform.domain.consulting.web.dto.request.CompanyInfoRequest;
import com.swOnCampus.AIPlatform.domain.consulting.web.dto.request.ConsultingRequest;
import com.swOnCampus.AIPlatform.domain.report.exception.ReportErrorCode;
import com.swOnCampus.AIPlatform.domain.report.web.dto.ReportingResponse;
import com.swOnCampus.AIPlatform.global.exception.GlobalException;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final TemplateEngine templateEngine;
    private final ConsultingRepository consultingRepository;
    private final ConsultingService consultingService; // 임시 코드

    @Value(value = "${report.font.path}")
    private String fontPath;

    @Override
    public ReportingResponse createReportingPdf(Long memberId,/* Long companyId*/
        CompanyInfoRequest request) {
//        Consulting consulting = consultingRepository.findByCompanyId(companyId)
//            .orElseThrow(() -> new GlobalException(ConsultingErrorCode.NOT_EXIST_CONSULTING));
        // 임시 코드
        ConsultingRequest consultingRequest = new ConsultingRequest(
            request.industry(),
            request.companySize(),
            request.painPoint()
        );

        ConsultingSave result = consultingService.getConsultingResult(consultingRequest);

//        String summaryHtml = consulting.getSummary().replace("\n", "<br />");
        String summaryHtml = result.summary().replace("\n", "<br />"); // 임시 코드
        String renderedMarkdown = renderMarkdown(summaryHtml);

        Map<String, Object> contextVariables = Map.of(
            "content", renderedMarkdown
        );
        String html = createHtmlFromTemplate(contextVariables);
        byte[] pdfBytes = convertHtmlToPdf(html);

        ReportingResponse response = new ReportingResponse(
//            consulting.getSummary(),
            result.summary(), // 임시 코드
            Base64.getEncoder().encodeToString(pdfBytes)
        );

        return response;
    }

    public String createHtmlFromTemplate(Map<String, Object> contextVariables) {
        String templateName = "reporting-summary-template";
        Context context = new Context();
        context.setVariables(contextVariables);

        return templateEngine.process(templateName, context);
    }

    public byte[] convertHtmlToPdf(String html) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        setFont(renderer);
        renderer.layout();
        renderer.createPDF(outputStream);

        return outputStream.toByteArray();
    }

    private void setFont(ITextRenderer renderer) {
        try {
            renderer.getFontResolver().addFont(
                new ClassPathResource(fontPath).getURL().toString(),
                BaseFont.IDENTITY_H,
                BaseFont.EMBEDDED
            );
        } catch (Exception e) {
            throw new GlobalException(ReportErrorCode.NOT_FOUND_FONT);
        }
    }

    public String renderMarkdown(String markdownText) {
        Parser parser = Parser.builder().build();
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(parser.parse(markdownText));
    }
}