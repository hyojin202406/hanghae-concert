package com.hhplu.hhplusconcert.app.infrastructure.payment;

import com.hhplu.hhplusconcert.app.domain.payment.entity.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentHistoryJpaRepository extends JpaRepository<PaymentHistory, Long> {
    Optional<PaymentHistory> findById(Long paymentHistoryId);

    List<PaymentHistory> findByUserId(Long userId);
}
