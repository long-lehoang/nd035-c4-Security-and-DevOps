package com.example.demo.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogUtils {
    private static final String START_REQUEST_LOG_PATTERN = "Start Request: {} {}, Query: {}";
    private static final String ERROR_REQUEST_LOG_PATTERN = "Error Request: {} {}, StatusCode: {}, Message: {}";
    private static final String END_REQUEST_LOG_PATTERN = "End Request: {} {}, StatusCode: {}, Response: {}";
    private LogUtils() {
        throw new IllegalStateException("Utility Class");
    }

    public static void errorRequest(String method, String uri, int statusCode, String message) {
        log.warn(ERROR_REQUEST_LOG_PATTERN, method, uri, statusCode, message);
    }

    public static void endRequest(String method, String uri, int statusCode, Object response) {
        log.info(END_REQUEST_LOG_PATTERN, method, uri, statusCode, response);
    }

    public static void startRequest(String method, String uri, String query) {
        log.info(START_REQUEST_LOG_PATTERN, method, uri, query);
    }
}
