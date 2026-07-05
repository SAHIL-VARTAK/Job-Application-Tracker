# ===== Build Stage =====
FROM eclipse-temurin:22-jdk AS build

RUN apt-get update && \
    apt-get install -y maven

WORKDIR /app

COPY checkstyle.xml .
COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests


# ===== Runtime Stage =====
FROM eclipse-temurin:22-jre

WORKDIR /app

RUN mkdir data

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]