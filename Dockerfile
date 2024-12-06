# JDK 17 Base 이미지 설정
FROM openjdk:17-ea

# jar 파일 위치를 변수로 설정
ARG JAR_FILE=build/libs/hhplus-concert-0.0.1-SNAPSHOT.jar

# jar 파일을 컨테이너 내부로 복사
COPY ${JAR_FILE} app.jar

# 외부 호스트의 8082 포트를 컨테이너의 8080 포트로 매핑
EXPOSE 8080

# 실행 명령어
CMD ["java", "-jar", "/app.jar"]