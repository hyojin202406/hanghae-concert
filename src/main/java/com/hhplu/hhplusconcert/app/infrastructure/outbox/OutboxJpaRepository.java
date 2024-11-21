package com.hhplu.hhplusconcert.app.infrastructure.outbox;

import com.hhplu.hhplusconcert.app.domain.outbox.entity.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxJpaRepository extends JpaRepository<Outbox, Long> {
    List<Outbox> findByPayload(String data);
}
