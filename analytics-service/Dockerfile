FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY ./analytics-service ./analytics-service
WORKDIR /app/analytics-service
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/analytics-service/target/*.jar analytics-service.jar
EXPOSE 8084
ENV TZ=Asia/Ho_Chi_Minh
ENTRYPOINT ["java", "-jar", "analytics-service.jar", "--spring.profiles.active=docker"] 