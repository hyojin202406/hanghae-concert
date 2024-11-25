import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
    vus: 1000, // 동시 요청 수
    duration: '20s', // 테스트 지속 시간
};

export default function () {
    const userId = Math.floor(Math.random() * 3) + 1; // 1~3 사이의 랜덤한 userId
    const url = `http://localhost:8080/api/queue/tokens/users/${userId}`; // 실제 API URL

    const headers = {
        'Content-Type': 'application/json',
    };

    const payload = JSON.stringify({}); // 요청 본문 데이터 (API 요구사항에 맞게 설정)

    const response = http.post(url,  { headers: headers });

    check(response, {
        'is status 200': (r) => r.status === 200,
    });

    sleep(1); // 각 요청 사이에 1초 대기
}
