FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY ./identity-service ./identity-service
WORKDIR /app/identity-service
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/identity-service/target/*.jar identity-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "identity-service.jar", "--spring.profiles.active=docker"] 