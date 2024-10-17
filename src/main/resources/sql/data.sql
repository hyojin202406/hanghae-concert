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

INSERT INTO users (name, point_amount)
VALUES
    ('UserA', 1000.00);
    ('UserB', 1000.00);
    ('UserC', 1000.00);

-- 스크립트 설명:
-- Concert 테이블에는 Concert A, Concert B 두 개의 콘서트가 추가됩니다.
-- Schedule 테이블에는 각 콘서트에 대한 스케줄을 3개 추가합니다.
-- Seat 테이블에는 각 스케줄에 좌석을 추가합니다. 상태는 'AVAILABLE'이며, 버전 필드는 0으로 설정하여 낙관적 락
