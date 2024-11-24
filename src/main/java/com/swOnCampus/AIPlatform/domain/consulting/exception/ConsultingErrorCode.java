package com.swOnCampus.AIPlatform.domain.consulting.exception;

import com.swOnCampus.AIPlatform.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ConsultingErrorCode implements ErrorCode {
    // 404
    NOT_EXIST_CONSULTING(HttpStatus.NOT_FOUND, "해당 기업에 대한 컨설팅 결과가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
