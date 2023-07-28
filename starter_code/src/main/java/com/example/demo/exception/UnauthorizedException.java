package com.example.demo.exception;

import com.example.demo.util.AppCode;
import lombok.Getter;

@Getter
public class UnauthorizedException extends BaseException{
    private final String message;

    public UnauthorizedException(String message){
        super(AppCode.USER_UNAUTHORIZED, message);
        this.message = message;
    }
}
