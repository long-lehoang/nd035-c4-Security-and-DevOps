package com.example.demo.config;

import com.example.demo.util.LogUtils;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Aspect
@Component
@AllArgsConstructor
public class ResponseLoggingAspect {

    private final HttpServletRequest httpServletRequest;
    private final HttpServletResponse httpServletResponse;

    @Pointcut("execution(* com.example.demo.controllers..*(..))")
    public void controllerMethods() {
    }

    @AfterReturning(pointcut = "controllerMethods()", returning = "response")
    public void logAfterResponse(ResponseEntity response) {
        LogUtils.endRequest(httpServletRequest.getMethod(),
                httpServletRequest.getRequestURI(),
                response.getStatusCodeValue(),
                response);
    }
}
