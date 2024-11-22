package com.swOnCampus.AIPlatform.global.exception;

import com.swOnCampus.AIPlatform.global.response.ApiResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Getter
@RequiredArgsConstructor
public class GlobalException extends RuntimeException {

    private final ErrorCode errorCode;

    @ExceptionHandler(GlobalException.class)
    public ApiResponse<?> handleGlobalException(
            GlobalException globalException
    ) {
        ErrorCode errorCode = globalException.getErrorCode();
        return ApiResponse.createSuccess(errorCode.getHttpStatus().value(), null, errorCode.getMessage());
    }
}