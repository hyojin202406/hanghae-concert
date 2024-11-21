package com.hhplu.hhplusconcert.app.domain.outbox.entity;

import com.hhplu.hhplusconcert.app.domain.outbox.OutboxStatus;
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

    public void publishedStaus() {
        this.outboxStatus = OutboxStatus.PUBLISHED;
    }
}
