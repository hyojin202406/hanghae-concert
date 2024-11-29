import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
    vus: 100, // 동시 요청 수
    duration: '20s', // 테스트 지속 시간
};

export default function () {
    const userId = Math.floor(Math.random() * 100000) + 1;
    const tokenUrl = `http://localhost:8082/api/queue/tokens/users/${userId}`;

    // 1. 대기열 토큰 생성 요청
    const tokenHeaders = {
        'Content-Type': 'application/json',
    };

    const tokenResponse = http.post(tokenUrl, JSON.stringify({}), { headers: tokenHeaders });

    check(tokenResponse, {
        'Token creation is status 200': (r) => r.status === 200,
    });

    if (tokenResponse.status !== 200) {
        console.error(`Failed to create token for userId: ${userId}, Status: ${tokenResponse.status}`);
        sleep(1);
        return;
    }

    const tokenData = tokenResponse.json();
    const queueToken = tokenData.queueToken; // 생성된 대기열 토큰

    // 2. 대기열 정보 폴링 조회
    const queueInfoUrl = 'http://localhost:8082/api/queue/tokens/users';
    const queueInfoHeaders = {
        'Content-Type': 'application/json',
        'QUEUE-TOKEN': queueToken, // 대기열 토큰 설정
    };

    let queuePosition = -1;
    let queueInfoResponse;

    // 대기열 상태가 -1이 아닐 경우 계속 폴링하여 상태 확인
    while (queuePosition !== -1) {
        queueInfoResponse = http.get(queueInfoUrl, { headers: queueInfoHeaders });

        check(queueInfoResponse, {
            'Queue info is status 200': (r) => r.status === 200,
        });

        if (queueInfoResponse.status !== 200) {
            console.error(`Failed to fetch queue info with token: ${queueToken}, Status: ${queueInfoResponse.status}`);
            sleep(1);
            return;
        }

        const queueInfo = queueInfoResponse.json();
        queuePosition = queueInfo.queuePosition;

        if (queuePosition !== -1) {
            console.log(`Waiting for position to be -1, current position: ${queuePosition}`);
            sleep(3); // 일정 시간 후 다시 대기열 상태를 확인
        }
    }

    // 3. 콘서트 스케줄 조회
    const concertScheduleUrl = 'http://localhost:8082/api/concerts/1/schedules';
    const concertScheduleHeaders = {
        'Content-Type': 'application/json',
        'QUEUE-TOKEN': queueToken, // 대기열 토큰 설정
    };

    const concertScheduleResponse = http.get(concertScheduleUrl, { headers: concertScheduleHeaders });

    check(concertScheduleResponse, {
        'Concert schedule is status 200': (r) => r.status === 200,
    });

    if (concertScheduleResponse.status !== 200) {
        console.error(`Failed to fetch concert schedule with token: ${queueToken}, Status: ${concertScheduleResponse.status}`);
        sleep(1);
        return;
    }

    // 4. 콘서트 좌석 정보 조회
    const concertSeatsUrl = 'http://localhost:8082/api/concerts/1/schedules/1/seats';
    const concertSeatsHeaders = {
        'Content-Type': 'application/json',
        'QUEUE-TOKEN': queueToken, // 대기열 토큰 설정
    };

    const concertSeatsResponse = http.get(concertSeatsUrl, { headers: concertSeatsHeaders });

    check(concertSeatsResponse, {
        'Concert seats is status 200': (r) => r.status === 200,
    });

    if (concertSeatsResponse.status !== 200) {
        console.error(`Failed to fetch concert seats with token: ${queueToken}, Status: ${concertSeatsResponse.status}`);
        sleep(1);
        return;
    }

    // 5. 좌석 예약 요청
    const reservationUrl = 'http://localhost:8082/api/reservations';
    const reservationHeaders = {
        'Content-Type': 'application/json',
        'QUEUE-TOKEN': queueToken,
    };

    const seatNumber = Math.floor(Math.random() * (172 - 120 + 1)) + 120;
    const seatIdsArr = [seatNumber];

    const reservationPayload = JSON.stringify({
        concertId: 1,
        scheduleId: 1,
        seatIdsArr: seatIdsArr, // 배열로 설정
        userId: userId
    });

    const reservationResponse = http.post(reservationUrl, reservationPayload, { headers: reservationHeaders });

    check(reservationResponse, {
        'Reservation is status 200': (r) => r.status === 200,
    });

    if (reservationResponse.status === 200) {
        console.log(`Reservation succeeded for userId: ${userId}, Seats: ${seatIdsArr}`);
    } else {
        console.error(`Reservation failed for userId: ${userId}, Status: ${reservationResponse.status}`);
        console.error(`Reservation failed. Response: ${reservationResponse.body}`);
    }

    sleep(1); // 요청 간 간격
}