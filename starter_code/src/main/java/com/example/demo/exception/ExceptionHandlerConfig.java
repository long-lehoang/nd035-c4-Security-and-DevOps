package com.example.demo.exception;

import com.example.demo.model.domain.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

@ControllerAdvice
@AllArgsConstructor
@Slf4j
public class ExceptionHandlerConfig extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        return ResponseEntity.badRequest().body(BaseResponse.userError(ex.getMessage()));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleCommonException(Exception exception) {
        log.debug(exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException exception) {
        log.debug(exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(BaseResponse.<String>builder()
                .message(messageSource.getMessage(exception.getCode().getMessage(), null, Locale.getDefault()))
                .build());
    }
}
