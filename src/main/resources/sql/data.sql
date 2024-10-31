-- 콘서트(Concert) 데이터 삽입
INSERT INTO concert (concert_name, created_at)
VALUES ('Concert A', NOW()),
       ('Concert B', NOW());

-- 스케줄(Schedule) 데이터 삽입
INSERT INTO schedule (concert_id, schedule_started_at, schedule_ended_at)
VALUES
    (1, '2024-10-01 19:00:00', '2024-10-01 21:00:00'),
    (1, '2024-10-02 19:00:00', '2024-10-02 21:00:00'),
    (2, '2024-10-03 19:00:00', '2024-10-03 21:00:00');

-- 좌석(Seat) 데이터 삽입
-- INSERT INTO Seat (schedule_id, reservation_id, seat_number, seat_price, status, created_at, expired_at, version)
-- VALUES
--     (1, NULL, 1, 100, 'AVAILABLE', NOW(), NOW() + INTERVAL 30 MINUTE, 0),
--     (1, NULL, 2, 100, 'AVAILABLE', NOW(), NOW() + INTERVAL 30 MINUTE, 0),
--     (1, NULL, 3, 120, 'AVAILABLE', NOW(), NOW() + INTERVAL 30 MINUTE, 0),
--     (2, NULL, 1, 150, 'AVAILABLE', NOW(), NOW() + INTERVAL 30 MINUTE, 0),
--     (2, NULL, 2, 150, 'AVAILABLE', NOW(), NOW() + INTERVAL 30 MINUTE, 0),
--     (3, NULL, 1, 200, 'AVAILABLE', NOW(), NOW() + INTERVAL 30 MINUTE, 0),
--     (3, NULL, 2, 200, 'AVAILABLE', NOW(), NOW() + INTERVAL 30 MINUTE, 0);

INSERT INTO seat (schedule_id, reservation_id, seat_number, seat_price, status, created_at, expired_at, version)
VALUES
    (1, NULL, 1, 100, 'AVAILABLE', NOW(), TIMESTAMPADD(MINUTE, 30, NOW()), 0),
    (1, NULL, 2, 100, 'AVAILABLE', NOW(), TIMESTAMPADD(MINUTE, 30, NOW()), 0),
    (1, NULL, 3, 120, 'AVAILABLE', NOW(), TIMESTAMPADD(MINUTE, 30, NOW()), 0),
    (2, NULL, 1, 150, 'AVAILABLE', NOW(), TIMESTAMPADD(MINUTE, 30, NOW()), 0),
    (2, NULL, 2, 150, 'AVAILABLE', NOW(), TIMESTAMPADD(MINUTE, 30, NOW()), 0),
    (3, NULL, 1, 200, 'AVAILABLE', NOW(), TIMESTAMPADD(MINUTE, 30, NOW()), 0),
    (3, NULL, 2, 200, 'AVAILABLE', NOW(), TIMESTAMPADD(MINUTE, 30, NOW()), 0);

INSERT INTO waiting_queue (queue_token, user_id, queue_status, issued_at, expired_at, version)
VALUES
    ('d8a74e6b-8946-4a57-9eaf-cb7f48e8c1a3', 1, 'EXPIRED', NOW(), TIMESTAMPADD(MINUTE, 10, NOW()), 0),
    ('d8a74e6b-8946-4a57-9eaf-cb7f48e8c1a4', 2, 'ACTIVE', NOW(), TIMESTAMPADD(MINUTE, 10, NOW()), 0),
    ('d8a74e6b-8946-4a57-9eaf-cb7f48e8c1a5', 3, 'WAITING', NOW(), TIMESTAMPADD(MINUTE, 10, NOW()), 0);

INSERT INTO users (name)
VALUES
    ('UserA'),
    ('UserB'),
    ('UserC');

-- Point 테이블 초기 데이터 삽입
INSERT INTO point (user_id, point_amount, version)
VALUES
    (1, 1000.00, 0),
    (2, 1000.00, 0),
    (3, 1000.00, 0);

-- PaymentHistory 테이블에 결제 내역 삽입
INSERT INTO payment_history (user_id, payment_id, payment_status, amount, payment_at)
VALUES
    (1, 1001, 'COMPLETED', 100.00, NOW()),
    (1, 1002, 'PENDING', 150.00, NOW()),
    (2, 1003, 'COMPLETED', 200.00, NOW()),
    (3, 1004, 'FAILED', 120.00, NOW()),
    (3, 1005, 'COMPLETED', 130.00, NOW());

INSERT INTO payment (reservation_id, amount, payment_status, payment_at)
VALUES
    (1, 100.00, 'COMPLETED', NOW()),
    (2, 150.00, 'PENDING', NOW()),
    (3, 200.00, 'FAILED', NOW()),
    (4, 120.00, 'COMPLETED', NOW()),
    (5, 130.00, 'PENDING', NOW());

INSERT INTO point (user_id, point_amount)
VALUES (1, 1000.00)
    ON DUPLICATE KEY UPDATE point_amount = 1000.00;