package com.hhplu.hhplusconcert.common.exception;

import com.hhplu.hhplusconcert.common.error.ErrorCode;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final ErrorCode errorCode;

    public BaseException(ErrorCode errorCode) {
        super(errorCode.getExternalMessage());
        this.errorCode = errorCode;
    }
}