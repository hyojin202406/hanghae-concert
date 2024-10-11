# 콘서트 예약 시스템 API 문서

## 유저 토큰 발급
### Request
- **URL**: `/api/queue/tokens/users/{userId}`
- **Method**: POST
- **URL Params**:
    - `userId`: `Long`

##### Response Body
```json
{
  "userId": 1,
  "queueToken": "d8a74e6b-8946-4a57-9eaf-cb7f48e8c1a5", 
  "issuedAt": "2024-10-08T10:00:00Z"
}
```

## 대기열 토큰 조회
### Request
- **URL**: `/api/queue/tokens/users`
- **Method**: GET
- **URL Params**:
    - `QUEUE-TOKEN`: String

### Response
```json
{
  "userId": 1,
  "queueToken": "d8a74e6b-8946-4a57-9eaf-cb7f48e8c1a5",
  "queuePosition": 5000,
  "lastActivedQueuePosition": 1000,
  "queueStatus": "WAITING",
  "issuedAt": "2024-10-08T10:00:00Z"
}
```

## 콘서트 일정 조회
### Request
- **URL**: `/api/concerts/{concertId}/schedules`
- **Method**: GET
- **URL Params**:
    - `concertId`: Long
- **Headers**:
    - `QUEUE-TOKEN`: String

### Response
```json
{
  "concertId": 1,
  "events": [
    {
      "scheduleId": 1,
      "concertAt": "2024-10-08T10:00:00"
    },
    {
      "scheduleId": 2,
      "concertAt": "2024-10-08T12:00:00"
    }
  ]
}
```

## 콘서트 좌석 조회
### Request
- **URL**: `/api/concerts/{concertId}/schedules/{scheduleId}/seats`
- **Method**: GET
- **URL Params**:
    - `concertId`: Long
    - `scheduleId`: Long
- **Headers**:
    - `QUEUE-TOKEN`: String

### Response
```json
{
  "concertId": 1,
  "scheduleId": 2,
  "allSeats": [
    {
      "seatId": 1,
      "seatNumber": 1,
      "seatStatus": "AVAILABLE",
      "seatPrice": 50000
    },
    {
      "seatId": 2,
      "seatNumber": 2,
      "seatStatus": "AVAILABLE",
      "seatPrice": 100000
    },
    {
      "seatId": 3,
      "seatNumber": 3,
      "seatStatus": "RESERVED",
      "seatPrice": 200000
    }
  ],
  "availableSeats": [
    {
      "seatId": 1,
      "seatNumber": 1,
      "seatStatus": "AVAILABLE",
      "seatPrice": 50000
    },
    {
      "seatId": 2,
      "seatNumber": 2,
      "seatStatus": "AVAILABLE",
      "seatPrice": 100000
    }
  ]
}
```
## 좌석 예약
### Request
- **URL**: `/api/reservations`
- **Method**: POST
- **Headers**:
    - `QUEUE-TOKEN`: String
- **Body**:
```json
{
  "userId": 1,
  "concertId": 1,
  "scheduleId": 1,
  "seatIdsArr": [1, 2]
}
```

### Response
```json
{
  "reservationId": 1,
  "concertId": 1,
  "concertName": "콘서트",
  "scheduledAt": "2024-10-08T10:00:00",
  "seats": [
    {
      "seatNumber": 10,
      "seatPrice": 10000
    },
    {
      "seatNumber": 11,
      "seatPrice": 15000
    }
  ],
  "totalPrice": 25000,
  "paymentStatus": "PAYMENT_PENDING"
}
```

## 결제
### Request
- **URL**: `/api/payments/users/{userId}`
- **Method**: POST
- **URL Params**:
    - `userId`: Long
- **Headers**:
    - `QUEUE-TOKEN`: String
### Response
```json
{
  "paymentId": 1,
  "amount": 25000,
  "paymentStatus": "PAYMENT_SUCCESS"
}
```

## 잔액 충전
### Request
- **URL**: `/api/payments/point/users/{userId}/recharge`
- **Method**: POST
- **URL Params**:
    - `userId`: Long
- **Headers**:
    - `QUEUE-TOKEN`: String
- **Body**:
```json
{
  "pointAmount": 50000
}
```
### Response
```json
{
  "userId": 1,
  "currentPointAmount": 40000
}
```
## 잔액 조회
### Request
- **URL**: `/api/payments/point/users/{userId}`
- **Method**: GET
- **URL Params**:
    - `userId`: Long
### Response
```json
{
  "userId": 1,
  "currentPointAmount": 40000
}
```