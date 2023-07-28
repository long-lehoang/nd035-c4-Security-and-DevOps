package com.example.demo.util;

public class Constants {
    private Constants() throws IllegalAccessException {
        throw new IllegalAccessException("Utility class");
    }
    public static final String PREFIX_AUTH_HEADER = "Bearer ";
    public static final String USER_ID_CLAIM = "user_id";
    public static final String USERNAME_CLAIM = "username";

    public static final String ROLE_ID_CLAIM = "role_id";
}
