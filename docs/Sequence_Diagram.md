# Sequence diagram

---

## 유저 토큰 발급 API

---

      주요 유저 대기열 토큰 기능
      • 서비스를 이용할 토큰을 발급받는 API를 작성합니다.
      • 토큰은 유저의 UUID 와 해당 유저의 대기열을 관리할 수 있는 정보 ( 대기 순서 or 잔여 시간 등 ) 를 포함합니다.
      • 이후 모든 API 는 위 토큰을 이용해 대기열 검증을 통과해야 이용 가능합니다.
      | 기본적으로 폴링으로 본인의 대기열을 확인한다고 가정하며, 다른 방안 또한 고려해보고 구현해 볼 수 있습니다.

```mermaid
sequenceDiagram
    actor Client
    participant API
    participant 유저서비스 as 유저
    participant 대기열서비스 as 대기열
    participant 토큰서비스 as 토큰
    participant DB

    Client->>API: 유저 토큰 발급 요청
    API->>유저서비스: Client 정보 조회 요청
    유저서비스->>DB: Client 정보 조회
    DB-->>유저서비스: Client 정보 반환
    유저서비스-->>API: Client 정보 반환
    opt 인증 실패
        API-->>Client: 인증 실패 메시지
    end
    API->>토큰서비스: 토큰 생성 요청 (유저 UUID)
    토큰서비스-->>DB: 토큰 저장
    DB-->>토큰서비스: 토큰 저장 완료
    토큰서비스-->>API: 토큰 전달
    API-->>Client: 토큰 반환

```

## 대기열 스케줄러

---

```mermaid

sequenceDiagram
    participant Scheduler
    participant Queue
    participant DB

    loop 주기적으로 실행
        Scheduler ->> Queue: 대기열 만료상태 변경  요청
        Queue ->> DB: 현재시간과 만료 시간을 비교하여 상태 변경
        DB ->> Queue: 만료상태 변경 완료
        Queue ->> Scheduler: 만료상태 변경 완료
        Scheduler ->> Queue: 대기 인원 활성화 요청 (N명)
        Queue ->> DB: 대기인원 우선순위를 확인하여 활성화 상태 변경 (N명)
        DB ->> Queue: 활성화 변경 완료 (N명)
        Queue ->> Scheduler: 활성화 변경 완료 (N명)
    end
```

## 대기열 정보 조회 API

---

```mermaid
sequenceDiagram
    actor Client
    participant API
    participant 토큰서비스 as 토큰
    participant 대기열서비스 as 대기열
    participant DB

    Client ->> API: 대기열 정보 조회 요청
    API ->> 토큰서비스: 토큰 유효성 검증 요청
    토큰서비스 ->> DB: 토큰 유효성 검증
    DB -->> 토큰서비스: 토큰 유효성 반환
    토큰서비스 -->> API: 토큰 유효성 반환
    opt 토큰 유효하지 않음
        토큰서비스-->>Client: 인증 실패 메시지
    end
    API ->> 대기열서비스: 대기열 정보 조회 요청 (마지막 활성자 순서, 자기 순서)
    대기열서비스 ->> DB: 대기열 정보 조회 요청 (마지막 활성자 순서, 자기 순서)
    DB -->> 대기열서비스: 대기열 정보 반환 (마지막 활성자 순서, 자기 순서)
    대기열서비스 -->> API: 대기열 정보 반환 (마지막 활성자 순서, 자기 순서)
    API -->> Client: 대기열 정보 반환 (마지막 활성자 순서, 자기 순서)
```

## 예약 가능 날짜,좌석 조회 API

---

	기본 예약 가능 날짜 / 좌석 API
	• 예약가능한 날짜와 해당 날짜의 좌석을 조회하는 API 를 각각 작성합니다.
	• 예약 가능한 날짜 목록을 조회할 수 있습니다.
	• 날짜 정보를 입력받아 예약가능한 좌석정보를 조회할 수 있습니다.
      좌석 정보는 1 ~ 50 까지의 좌석번호로 관리됩니다.

### 예약 가능 날짜 조회 API
```mermaid
sequenceDiagram
    actor Client
    participant API
    participant 토큰서비스 as 토큰
    participant 콘서트서비스 as 콘서트
    participant DB

    Client ->> API: 예약 가능 날짜 조회 요청
    API ->> 토큰서비스: 토큰 유효성 요청
    토큰서비스 ->> DB: 토큰 유효성 검증
    DB -->> 토큰서비스: 토큰 유효성 반환
    토큰서비스 -->> API: 토큰 유효성 반환
    opt 토큰이 유효하지 않음
        API --> Client: 예외처리
    end 
    API ->> 콘서트서비스: 예약 가능 날짜 조회 요청
    콘서트서비스 ->> DB: 예약 가능 날짜 조회
    DB -->> 콘서트서비스: 예약 가능 날짜 반환
    콘서트서비스 -->> API: 예약 가능 날짜 반환
    API -->> Client: 예약 가능 날짜 반환

```

### 예약 가능 좌석 조회 API
```mermaid
sequenceDiagram
    actor Client
    participant API
    participant 토큰서비스
    participant 콘서트서비스
    participant DB

    Client ->> API: 예약 가능 좌석 조회 요청
    API ->> 토큰서비스: 토큰 유효성 요청
    토큰서비스 ->> DB: 토큰 유효성 검증
    DB -->> 토큰서비스: 토큰 유효성 반환
    토큰서비스 -->> API: 토큰 유효성 반환
    opt 토큰이 유효하지 않음
        API --> Client: 예외처리
    end
    API ->> 콘서트서비스: 예약 가능 좌석 조회 요청
    콘서트서비스 ->> DB: 예약 가능 좌석 조회
    DB -->> 콘서트서비스: 예약 가능 좌석 반환
    콘서트서비스 -->> API: 예약 가능 좌석 반환
    API -->> Client: 예약 가능 좌석 반환

```

## 잔액 충전 / 조회 API

---
      기본 잔액 충전 / 조회 API
      • 결제에 사용될 금액을 API 를 통해 충전하는 API 를 작성합니다.
      • 사용자 식별자 및 충전할 금액을 받아 잔액을 충전합니다.
      • 사용자 식별자를 통해 해당 사용자의 잔액을 조회합니다.
### 잔액 충전 API
```mermaid
sequenceDiagram
    actor Client
    participant API
    participant 토큰서비스 as 토큰
    participant 잔액서비스 as 잔액
    participant DB

    Client ->> API: 대기열 충전 요청
    API ->> 잔액서비스: 잔액 충전 요청
    잔액서비스 ->> DB: 잔액 충전
    DB -->> 잔액서비스: 잔액 내역 반환
    잔액서비스 -->> API: 잔액 내역 반환
    API -->> Client: 잔액 내역 반환
```

### 잔액 조회 API
```mermaid
sequenceDiagram
    actor Client
    participant API
    participant 토큰서비스 as 토큰
    participant 잔액서비스 as 잔액
    participant DB

    Client ->> API: 잔액 조회 요청
    API ->> 잔액서비스: 잔액 조회 요청
    잔액서비스 ->> DB: 잔액 조회
    DB -->> 잔액서비스: 잔액 반환
    잔액서비스 -->> API: 잔액 반환
    API -->> Client: 잔액 반환
```

## 좌석 예약 API
```mermaid
sequenceDiagram
    actor Client
    participant API
    participant 토큰서비스 as 토큰
    participant 잔액서비스 as 잔액
    participant 예약서비스 as 예약
    participant DB

    Client ->> API: 대기열 정보 조회 요청
    API ->> 토큰서비스: 토큰 유효성 검증 요청
    토큰서비스 ->> DB: 토큰 유효성 검증
    DB -->> 토큰서비스: 토큰 유효성 반환
    토큰서비스 -->> API: 토큰 유효성 반환
    opt 토큰 유효하지 않음
        토큰서비스-->>Client: 인증 실패 메시지
    end
    API ->> 예약서비스: 좌석 예약 요청
    예약서비스 ->> DB: 좌석 예약 (임시배정 5분)
    alt 결제 완료
        DB -->> 예약서비스: 좌석 예약 완료
        예약서비스 -->> API: 좌석 예약 완료
        API -->> Client: 좌석 예약 완료
    else 임시배정 5분 초과
        API -->> Client: 예약 실패
    end
```


## 결제 API

---
      주요 결제 API
      • 결제 처리하고 결제 내역을 생성하는 API 를 작성합니다.
      • 결제가 완료되면 해당 좌석의 소유권을 유저에게 배정하고 대기열 토큰을 만료시킵니다.

```mermaid
sequenceDiagram
    actor Client
    participant API
    participant 토큰서비스 as 토큰
    participant 대기열서비스 as 대기열
    participant 결제서비스 as 결제
    participant DB

    Client ->> API: 결제 요청
    API ->> 토큰서비스: 토큰 유효성 검증 요청
    토큰서비스 ->> DB: 토큰 유효성 검증
    DB -->> 토큰서비스: 토큰 유효성 반환
    opt 토큰 유효하지 않음
        토큰서비스-->>Client: 인증 실패 메시지
    end
    API ->> 결제서비스: 결제 요청
    결제서비스 ->> DB:  Client 잔액 조회
    DB -->> 결제서비스: Client 잔액 반환
    opt Client 잔액이 충분하지 않음
        결제서비스-->>API: 결제 실패
        API-->>Client: 결제 실패
    end
    결제서비스 ->> DB:  Client 잔액 차감
    결제서비스 ->> DB:  결제 요청
    DB -->> 결제서비스: 결제 내역 반환
    결제서비스 -->> API: 결제 내역 반환
    API ->> 결제서비스: 좌석 예약 요청
    결제서비스 ->> DB:  좌석 예약
    DB -->> 결제서비스: 결제 내역 반환
    결제서비스 -->> API: 결제 내역 반환
    API ->> 대기열서비스: 대기열 토큰 만료 요청
    대기열서비스 ->> DB: 대기열 토큰 만료
    DB -->> 대기열서비스: 만료 처리 완료
    대기열서비스 -->> API: 만료 처리 완료
    API -->> Client: 결제 완료
```