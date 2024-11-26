package com.swOnCampus.AIPlatform.domain.report.exception;

import com.swOnCampus.AIPlatform.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReportErrorCode implements ErrorCode {

    // 404
    NOT_FOUND_FONT(HttpStatus.NOT_FOUND, "해당 경로에는 폰트가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
