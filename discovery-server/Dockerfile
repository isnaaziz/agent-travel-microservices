# Stage 1: Build stage using Maven and JDK 21
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY discovery-server/pom.xml discovery-server/
RUN mvn dependency:go-offline -B -pl discovery-server || true
COPY discovery-server/src discovery-server/src
RUN mvn clean package -pl discovery-server -DskipTests

# Stage 2: Tiny JRE Alpine runtime
FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp
WORKDIR /app
COPY --from=builder /app/discovery-server/target/*.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/app.jar"]
