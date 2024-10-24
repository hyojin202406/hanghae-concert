package com.hhplu.hhplusconcert.app.domain.point.entity;

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
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("추가할 금액은 0 이상이어야 합니다.");
        }
        this.pointAmount = this.pointAmount.add(amount);
    }

    public void subtractPointAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("차감할 금액은 0 이상이어야 합니다.");
        }
        if (this.pointAmount.compareTo(amount) < 0) {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }
        this.pointAmount = this.pointAmount.subtract(amount);
    }

    public void recharge(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("충전할 금액은 0보다 커야 합니다.");
        }
        addPointAmount(amount);
    }
}