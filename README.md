# 콘서트 예약 서비스
**콘서트 예약 서비스**는 사용자가 콘서트 좌석을 실시간으로 예약할 수 있도록 설계된 시스템입니다.  
대기열 관리, 좌석 예약, 결제 처리 등 주요 기능을 포함하며, 안정적이고 확장 가능한 아키텍처로 구현되었습니다.

이 서비스는 다음과 같은 목표를 가지고 개발되었습니다.
1. **효율적인 대기열 관리**: Redis를 활용해 대기열 처리 속도를 최적화.
2. **실시간 좌석 예약**: 동시성 문제를 해결해 다수 사용자가 안정적으로 예약 가능.
3. **트랜잭션 관리**: 데이터 일관성을 유지하며, 성능과 확장성을 고려한 설계.

---

## 서비스 설계
- [**유즈케이스 분석**](https://xmind.ai/share/Su2VagJq?xid=nIqEcML4): 서비스 주요 흐름과 사용자 행동을 체계적으로 분석.
- [**마일스톤**](https://github.com/users/hyojin202406/projects/1): 개발 진행 상황 및 주요 목표를 정리한 프로젝트 관리 내용.
- [**시퀀스 다이어그램**](docs/Sequence_Diagram.md): 주요 기능의 실행 흐름을 시각적으로 표현.

---

## 데이터 및 구조 설계
- [**ERD**](docs/ERD.md): 서비스 데이터베이스 구조 설계.
- [**API 명세**](docs/API.md): API 요청/응답 스펙과 데이터 구조 상세 설명.
- [**프로젝트 구조**](docs/Project.md): 프로젝트 디렉토리 구성 및 각 계층의 역할.

---

## 구현 및 테스트
- [**GUI Swagger 접속**](http://localhost:8080/swagger-ui/index.html): API 테스트와 문서화를 위한 Swagger UI 링크.
- [**API-Swagger 명세**](/docs/API-Swagger.md): API 구현 내용과 상세 설명을 포함한 문서.


---

## 성능 개선 및 분석
- [**동시성 제어 분석**](https://velog.io/@hj20220908/%EB%8F%99%EC%8B%9C%EC%84%B1-%EC%A0%9C%EC%96%B4-%EB%B6%84%EC%84%9D): 동시성 문제 해결을 위한 제어 전략.
- [**캐싱 및 Redis 활용**](https://velog.io/@hj20220908/%EC%A1%B0%ED%9A%8C-%EC%84%B1%EB%8A%A5-%EA%B0%9C%EC%84%A0%EC%9D%84-%EC%9C%84%ED%95%9C-%EC%BA%90%EC%8B%B1-%EB%B0%8F-Redis-%ED%99%9C%EC%9A%A9-%EB%B0%A9%EC%95%88-%EB%B6%84%EC%84%9D): Redis를 활용한 조회 성능 개선 사례.
- [**쿼리 최적화 사례 분석**](https://velog.io/@hj20220908/%EC%9D%B8%EB%8D%B1%EC%8A%A4-%EC%B5%9C%EC%A0%81%ED%99%94%EB%A5%BC-%ED%86%B5%ED%95%9C-%EC%BF%BC%EB%A6%AC-%EC%84%B1%EB%8A%A5-%EA%B0%9C%EC%84%A0-%EC%82%AC%EB%A1%80-%EB%B6%84%EC%84%9D): 인덱스 최적화를 통한 DB 성능 개선.

---

## 확장성 및 트랜잭션 관리
- [**서비스 분리 및 트랜잭션 관리 전략**](https://velog.io/@hj20220908/%ED%99%95%EC%9E%A5%EC%84%B1%EC%9D%84-%EA%B3%A0%EB%A0%A4%ED%95%9C-%EC%84%9C%EB%B9%84%EC%8A%A4-%EB%B6%84%EB%A6%AC-%EB%B0%8F-%ED%8A%B8%EB%9E%9C%EC%9E%AD%EC%85%98-%EA%B4%80%EB%A6%AC-%EC%A0%84%EB%9E%B5): 확장성을 고려한 서비스 설계 및 트랜잭션 관리 사례.

---

## 부하 테스트
- [**k6 부하 테스트 보고서**](https://velog.io/@hj20220908/k6-%EB%B6%80%ED%95%98%ED%85%8C%EC%8A%A4%ED%8A%B8): k6를 활용한 부하 테스트 결과 및 분석.

---

## 기술 스택
- JDK 17: 안정적인 장기 지원 버전, 많이 사용하는 최신 LTS 버전.
- Spring Boot: 빠르고 간편한 웹 애플리케이션 개발 환경 제공.
- Data JPA: 데이터베이스 접근을 추상화하여 간편한 ORM 지원.
- H2 데이터베이스: 인메모리 DB로 테스트 환경에서 빠른 실행 가능.
- JUnit: 테스트 코드 작성을 위한 표준적인 테스트 프레임워크.
- AssertJ: 가독성 높은 테스트 코드 작성을 위한 assertion 제공.