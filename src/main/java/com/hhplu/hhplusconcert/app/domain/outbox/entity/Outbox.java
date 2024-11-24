package com.hhplu.hhplusconcert.app.domain.outbox.entity;

import com.hhplu.hhplusconcert.app.domain.outbox.OutboxStatus;
import com.hhplu.hhplusconcert.common.error.ErrorCode;
import com.hhplu.hhplusconcert.common.exception.BaseException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Outbox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "event_key")
    private String eventKey;

    @Column(nullable = false, name = "event_type")
    private String eventType;

    @Column(nullable = false)
    private String payload;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "outbox_status")
    private OutboxStatus outboxStatus;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    public static Outbox createOutbox(String type, String eventKey, String payload) {
        if (!type.equals("payment") && !type.equals("reservation")) {
            throw new BaseException(ErrorCode.COMMON_BAD_REQUEST);
        }

        Outbox outbox = Outbox.builder()
                .eventKey(eventKey)
                .eventType(type)
                .payload(payload)
                .outboxStatus(OutboxStatus.INIT)
                .createdAt(LocalDateTime.now())
                .build();

        return outbox;
    }

    public void publishedStaus() {
        this.outboxStatus = OutboxStatus.PUBLISHED;
    }
}
