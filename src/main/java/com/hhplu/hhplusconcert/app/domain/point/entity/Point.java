package com.hhplu.hhplusconcert.app.domain.point.entity;

import com.hhplu.hhplusconcert.common.error.ErrorCode;
import com.hhplu.hhplusconcert.common.exception.BaseException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "point_amount", nullable = false)
    private BigDecimal pointAmount;

    public void addPointAmount(BigDecimal amount) {
        this.pointAmount = this.pointAmount.add(amount);
    }

    public void subtractPointAmount(BigDecimal amount, BigDecimal totalAmount) {
        if (amount.compareTo(totalAmount) < 0) {
            throw new BaseException(ErrorCode.POINT_BAD_RECHARGE_REQUEST);
        }
        if (totalAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new BaseException(ErrorCode.POINT_INVALID_DEDUCT_AMOUNT);
        }
        if (this.pointAmount.compareTo(totalAmount) < 0) {
            throw new BaseException(ErrorCode.POINT_BAD_RECHARGE_REQUEST);
        }
        this.pointAmount = this.pointAmount.subtract(totalAmount);
    }

    public void recharge(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BaseException(ErrorCode.POINT_INVALID_RECHARGE_AMOUNT);
        }
        addPointAmount(amount);
    }
}