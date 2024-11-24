package com.swOnCampus.AIPlatform.domain.report.service;

import com.lowagie.text.pdf.BaseFont;
import com.swOnCampus.AIPlatform.domain.report.web.dto.ReportingSummaryRequest;
import com.swOnCampus.AIPlatform.global.exception.GlobalException;
import java.io.ByteArrayOutputStream;
import java.util.Map;

import com.swOnCampus.AIPlatform.domain.report.exception.ReportErrorCode;
import lombok.RequiredArgsConstructor;
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

    @Value(value = "${report.font.path}")
    private String fontPath;

    @Override
    public byte[] createReportingSummaryPdf(ReportingSummaryRequest request) {
        Map<String, Object> contextVariables = Map.of(
                "title", request.title(),
                "content", request.content()
        );
        String html = createHtmlFromTemplate(contextVariables);

        return convertHtmlToPdf(html);
    }

    private String createHtmlFromTemplate(Map<String, Object> contextVariables) {
        String templateName = "reporting-summary-template";
        Context context = new Context();
        context.setVariables(contextVariables);

        return templateEngine.process(templateName, context);
    }

    private byte[] convertHtmlToPdf(String html) {
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
}
