FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY ./eureka-server ./eureka-server
WORKDIR /app/eureka-server
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/eureka-server/target/*.jar eureka-server.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "eureka-server.jar"]
