FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY ./submission-service ./submission-service
WORKDIR /app/submission-service
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/submission-service/target/*.jar submission-service.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "submission-service.jar", "--spring.profiles.active=docker"] 