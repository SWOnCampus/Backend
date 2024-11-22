package com.swOnCampus.AIPlatform.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private int status;
    private T data;
    private String message;

    // 성공
    public static <T> ApiResponse<T> createSuccess(int status, T data, String message) {
        return new ApiResponse<>(status, data, message);
    }

    // 실패
    public static <T> ApiResponse<T> createFailWithoutData(int status, String message) {
        return new ApiResponse<>(status, null, message);
    }
}