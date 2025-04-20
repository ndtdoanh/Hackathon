FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY ./api-gateway ./api-gateway
WORKDIR /app/api-gateway
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/api-gateway/target/*.jar api-gateway.jar
EXPOSE 9191
ENTRYPOINT ["java", "-jar", "api-gateway.jar", "--spring.profiles.active=docker"]
