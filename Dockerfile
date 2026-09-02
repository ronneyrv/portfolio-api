FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

COPY pom.xml mvnw ./
COPY .mvn .mvn

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY src src

RUN ./mvnw clean package -DskipTests


FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/target/portfolioapi-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
