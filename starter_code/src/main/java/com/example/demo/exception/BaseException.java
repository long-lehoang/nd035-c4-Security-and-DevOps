package com.example.demo.exception;

import com.example.demo.model.domain.BaseResponse;
import com.example.demo.util.AppCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BaseException extends RuntimeException{
    private final AppCode code;
    private final String message;
}
