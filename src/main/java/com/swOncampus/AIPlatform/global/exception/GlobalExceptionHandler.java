package com.swOncampus.AIPlatform.global.exception;

import com.swOncampus.AIPlatform.global.dto.response.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ApiResponse<?> handleGlobalException(GlobalException globalException) {
        ErrorCode errorCode = globalException.getErrorCode();
        return new ApiResponse<>(errorCode.getHttpStatus().value(), errorCode.getMessage());
    }
}
