FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY ./hackathon-service ./hackathon-service
WORKDIR /app/hackathon-service
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/hackathon-service/target/*.jar hackathon-service.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "hackathon-service.jar", "--spring.profiles.active=docker"] 