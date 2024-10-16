package com.hhplu.hhplusconcert.app.domain.queue.entity;

import com.hhplu.hhplusconcert.app.domain.queue.QueueStatus;
import com.hhplu.hhplusconcert.app.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WaitingQueue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "queue_token")
    private String queueToken;

    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "queue_status")
    private QueueStatus queueStatus;

    @Column(name = "issued_at")
    private LocalDateTime issuedAt;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    public void token(User user) {
        user.generateToken();
        this.queueToken = user.getToken();
        this.userId = user.getId();
        this.queueStatus = QueueStatus.WAITING;
        this.issuedAt = LocalDateTime.now();
        this.expiredAt = LocalDateTime.now().plusMinutes(10);
    }

    public void expireToken(QueueStatus queueStatus) {
        this.queueStatus = queueStatus;
    }
}