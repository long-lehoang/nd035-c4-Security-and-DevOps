package com.example.demo.exception;

import com.example.demo.model.domain.BaseResponse;
import com.example.demo.util.AppCode;
import com.example.demo.util.LogUtils;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@ControllerAdvice
@AllArgsConstructor
public class ExceptionHandlerConfig extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;
    private final HttpServletRequest httpServletRequest;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        LogUtils.errorRequest(httpServletRequest.getMethod(),
                httpServletRequest.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage());

        return ResponseEntity.badRequest().body(BaseResponse
                .userError(messageSource.getMessage(
                        AppCode.INVALID_REQUEST.getMessage(),
                        null,
                        Locale.getDefault())));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleCommonException(Exception exception) {
        LogUtils.errorRequest(httpServletRequest.getMethod(),
                httpServletRequest.getRequestURI(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException exception) {
        LogUtils.errorRequest(httpServletRequest.getMethod(),
                httpServletRequest.getRequestURI(),
                HttpStatus.UNAUTHORIZED.value(),
                exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(BaseResponse.<String>builder()
                .message(messageSource.getMessage(exception.getCode().getMessage(), null, Locale.getDefault()))
                .build());
    }
}
