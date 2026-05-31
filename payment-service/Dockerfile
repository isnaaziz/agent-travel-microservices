# Stage 1: Build stage using Maven and JDK 21
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY payment-service/pom.xml payment-service/
RUN mvn dependency:go-offline -B -pl payment-service || true
COPY payment-service/src payment-service/src
RUN mvn clean package -pl payment-service -DskipTests

# Stage 2: Tiny JRE Alpine runtime
FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp
WORKDIR /app
COPY --from=builder /app/payment-service/target/*.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/app.jar"]
