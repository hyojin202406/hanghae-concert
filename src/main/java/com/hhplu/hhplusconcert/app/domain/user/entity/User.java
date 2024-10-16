package com.hhplu.hhplusconcert.app.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String token; // 토큰 필드

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "point_amount", nullable = false)
    private BigDecimal pointAmount;

    public void generateToken() {
        this.token = UUID.nameUUIDFromBytes(this.name.getBytes()).toString();
    }

    // 포인트 추가 메서드
    public void addPointAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("추가할 금액은 0 이상이어야 합니다.");
        }
        this.pointAmount = this.pointAmount.add(amount);
    }

    // 포인트 차감 메서드
    public void subtractPointAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("차감할 금액은 0 이상이어야 합니다.");
        }
        if (this.pointAmount.compareTo(amount) < 0) {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }
        this.pointAmount = this.pointAmount.subtract(amount);
    }
}