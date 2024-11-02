// script.js
import http from 'k6/http';
import { check } from 'k6';
import { randomIntBetween, randomString } from "https://jslib.k6.io/k6-utils/1.1.0/index.js";

export let options = {
    vus: 300, // 가상 사용자 수
    duration: '10m', // 테스트 지속 시간
};

export default function () {
    // 랜덤 데이터 생성
    const userId = randomString(8);
    const storeId = randomIntBetween(1, 100);
    const visitAt = new Date().toISOString().split("T")[0];
    const visitTime = `${randomIntBetween(9, 21)}:00`;
    const guestCount = randomIntBetween(1, 5);

    // JSON 바디 구성
    const body = JSON.stringify({
        userId: userId,
        storeId: storeId,
        visitAt: visitAt,
        visitTime: visitTime,
        guestCount: guestCount
    });

    // HTTP POST 요청 설정
    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    // POST 요청 보내기
    let response = http.post('http://localhost:8181/api/reservations', body, params);

    // 응답 확인
    check(response, {
        'is status 200': (r) => r.status === 200,
    });
}