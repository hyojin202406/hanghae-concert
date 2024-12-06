## 도커
### 도커 컴포즈 설치:
- docker-compose -p concert -f docker-compose-concert.yml up -d

### 스프링 이미지 빌드:
- docker build -t my-spring-app .
- docker run -d   --name my-spring-app   -p 8082:8080   --memory="512m"   --memory-swap="1g"   --cpus="2.0"  my-spring-app
---
## 레디스:
### 레디스 컨테이너 접속:
- docker exec -it concert-redis redis-cli
### 레디스 명령어:
- ZRANGE waitingUserQueue 0 -1 WITHSCORES
- LRANGE activeUserQueue 0 -1
- DEL activeUserQueue
- EXPIRE activeUserQueue 10

---

##  카프카
### 카프카 컨테이너 접속: 
- docker exec -it kafka bash 
### 토픽 생성:
- kafka-topics --bootstrap-server localhost:9092 --create --topic test-topic --partitions 1 --replication-factor 1 
- 이 명령어는 test-topic이라는 이름의 토픽을 생성합니다.

### 토픽 목록 확인
   생성된 토픽이 있는지 확인합니다.
    - kafka-topics --bootstrap-server localhost:9092 --list

### 메시지 전송 (Producer)
- 토픽에 메시지를 보내기 위해 Producer를 실행합니다.
    - kafka-console-producer --bootstrap-server localhost:9092 --topic test-topic
    - kafka-console-producer --bootstrap-server localhost:9092 --topic payment-topic

### 메시지 수신 (Consumer)
- 토픽에서 메시지를 읽기 위해 Consumer를 실행합니다.
  - kafka-console-consumer --bootstrap-server localhost:9092 --topic test-topic --from-beginning
  - kafka-console-consumer --bootstrap-server localhost:9092 --topic payment-topic --from-beginning
  - kafka-console-consumer --bootstrap-server localhost:9092 --topic topic --from-beginning

