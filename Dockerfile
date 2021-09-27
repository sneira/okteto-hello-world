# syntax = docker/dockerfile:experimental
FROM gradle:latest as builder

WORKDIR /app
COPY . .
RUN --mount=type=cache,target=/home/gradle/.gradle /app/gradlew bootJar

FROM adoptopenjdk/openjdk11-openj9:jdk-11.0.1.13-alpine-slim

WORKDIR /app
COPY --from=builder /app/build/libs/*.jar application.jar

CMD ["sh", "-c","java -Djava.security.egd=file:/dev/./urandom -jar /app/application.jar"]