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

    public void changePointAmount(BigDecimal addPointAmount) {
        this.pointAmount = this.pointAmount.add(addPointAmount);
    }
}