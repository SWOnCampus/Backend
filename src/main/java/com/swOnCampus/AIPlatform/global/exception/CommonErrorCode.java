package com.swOnCampus.AIPlatform.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {
    // 404
    NOT_EXIST_MEMBER(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
