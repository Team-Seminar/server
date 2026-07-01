# 1단계: 빌드
FROM gradle:8.5-jdk17 AS build
COPY --chown=gradle:gradle . /home/app
WORKDIR /home/app
RUN gradle build -x test --no-daemon

# 2단계: 실행
FROM eclipse-temurin:17-jre-alpine
EXPOSE 8080
COPY --from=build /home/app/build/libs/*.jar app.jar
# 힙 메모리 최대치를 300MB로 제한하여 무료 플랜에서 튕기는 것을 방지
ENTRYPOINT ["java", "-Xmx300m", "-jar", "/app.jar"]