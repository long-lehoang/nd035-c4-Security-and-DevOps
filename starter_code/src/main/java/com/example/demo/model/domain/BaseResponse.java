package com.example.demo.model.domain;

import com.example.demo.util.AppCode;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseResponse<T> {
    private String message;
    private T data;

    public static <T> BaseResponse<T> ok(T data) {
        return BaseResponse.<T>builder()
                .message(AppCode.SUCCESS.getMessage())
                .data(data)
                .build();
    }

    public static BaseResponse<String> userError(String message) {
        return BaseResponse.<String>builder()
                .message(message)
                .build();
    }
}
