FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

COPY . .

RUN chmod +x ./mvnw

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY --from=builder /app/target/bitscafe-0.0.1-SNAPSHOT.jar app.jar

COPY dbinit /app/sql

ENTRYPOINT ["java", "-jar", "app.jar"]
