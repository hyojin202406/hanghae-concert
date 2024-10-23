package com.hhplu.hhplusconcert.app.common.exception;

import com.hhplu.hhplusconcert.app.common.error.ErrorCode;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final ErrorCode errorCode;

    public BaseException(ErrorCode errorCode) {
        super(errorCode.getExternalMessage());
        this.errorCode = errorCode;
    }
}