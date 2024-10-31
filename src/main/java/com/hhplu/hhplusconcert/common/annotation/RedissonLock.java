package com.hhplu.hhplusconcert.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedissonLock {
    String value();                 // 락 키
    long waitTime() default 10000L; // 대기 시간
    long leaseTime() default 4000L; // 락 소유 시간
}