package com.swOnCampus.AIPlatform.domain.consulting.exception;

import com.swOnCampus.AIPlatform.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ConsultingErrorCode implements ErrorCode {
    // 404
    NOT_EXIST_USER(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
