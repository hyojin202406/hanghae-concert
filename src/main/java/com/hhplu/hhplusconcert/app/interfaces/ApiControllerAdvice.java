package com.hhplu.hhplusconcert.app.interfaces;

import com.hhplu.hhplusconcert.common.error.ErrorCode;
import com.hhplu.hhplusconcert.common.exception.BaseException;
import com.hhplu.hhplusconcert.common.error.ErrorResponse;
import jakarta.persistence.OptimisticLockException;
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

    @ExceptionHandler(value = OptimisticLockException.class)
    public ResponseEntity<ErrorResponse> handleOptimisticLockException(OptimisticLockException e) {
        log.error("Optimistic lock failure: " + e.getMessage(), e);

        ErrorCode errorCode = ErrorCode.SEAT_RESERVATION_FAILED;
        return ResponseEntity
                .status(HttpStatus.CONFLICT) // 낙관적 락 충돌의 경우, 409 Conflict를 사용할 수 있습니다.
                .body(new ErrorResponse(errorCode.getErrorCode(), "요청이 실패했습니다. 다시 시도해 주세요."));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(500)
                .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "에러가 발생했습니다."));
    }

}
