FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY ./communication-service ./communication-service
WORKDIR /app/communication-service
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/communication-service/target/*.jar communication-service.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "communication-service.jar", "--spring.profiles.active=docker"] 