# Stage 1: Build stage using Maven and JDK 21
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY auth-service/pom.xml auth-service/
RUN mvn dependency:go-offline -B -pl auth-service || true
COPY auth-service/src auth-service/src
RUN mvn clean package -pl auth-service -DskipTests

# Stage 2: Tiny JRE Alpine runtime
FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp
WORKDIR /app
COPY --from=builder /app/auth-service/target/*.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/app.jar"]
