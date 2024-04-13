FROM maven:3.8.4-openjdk-17 AS builder

WORKDIR /app

COPY pom.xml ./
COPY src ./src/

RUN mvn clean package

FROM openjdk:17-oracle

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 7777

ENTRYPOINT ["java", "-jar", "app.jar"]
