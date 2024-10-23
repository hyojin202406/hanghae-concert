package com.hhplu.hhplusconcert.config;

import com.hhplu.hhplusconcert.app.common.error.ErrorCode;
import com.hhplu.hhplusconcert.app.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ApiControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<ErrorResponse> BaseException(BaseException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error(errorCode.getInternalMessage());

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(new ErrorResponse(errorCode.getErrorCode(), errorCode.getExternalMessage()));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(500)
                .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "에러가 발생했습니다."));
    }

}
