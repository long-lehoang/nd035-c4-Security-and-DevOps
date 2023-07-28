package com.example.demo.util;

public enum AppCode {
    USER_UNAUTHORIZED("code.user.unauthorized"),
    SUCCESS("code.success"),
    USER_ERROR("code.user.error");
    final String message;

    AppCode(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

}
