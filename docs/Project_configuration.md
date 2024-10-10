# 프로젝트

---

## 프로젝트 구조
```txt
hhplusconcert/
├── src/main/java/com/hhplu/
│                     └── hhplusconcert/
│                         ├── application/
│                         ├── domain/
│                         ├── infrastructure/
│                         └── interfaces/
│                             └──api
│                                └──concert
│                                └──queue
│                                └──reservation
└─── resources/
```

## 기술 스택
- JDK 17: 안정적인 장기 지원 버전, 많이 사용하는 최신 LTS 버전.
- Spring Boot: 빠르고 간편한 웹 애플리케이션 개발 환경 제공.
- Data JPA: 데이터베이스 접근을 추상화하여 간편한 ORM 지원.
- H2 데이터베이스: 인메모리 DB로 테스트 환경에서 빠른 실행 가능.
- JUnit: 테스트 코드 작성을 위한 표준적인 테스트 프레임워크.
- AssertJ: 가독성 높은 테스트 코드 작성을 위한 assertion 제공.