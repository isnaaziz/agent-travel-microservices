# Stage 1: Build stage using Maven and JDK 21
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY discovery-server/pom.xml discovery-server/
COPY config-server/pom.xml config-server/
COPY api-gateway/pom.xml api-gateway/
COPY auth-service/pom.xml auth-service/
COPY destination-service/pom.xml destination-service/
COPY booking-service/pom.xml booking-service/
COPY payment-service/pom.xml payment-service/

COPY config-server/src config-server/src
RUN mvn clean package -pl config-server -DskipTests

# Stage 2: Tiny JRE Alpine runtime
FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp
WORKDIR /app
COPY --from=builder /app/config-server/target/*.jar /app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=native,docker", "-jar", "/app.jar"]
