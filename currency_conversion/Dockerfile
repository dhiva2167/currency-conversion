# Build stage
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copy maven wrapper and pom.xml first for layer caching
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw

# Download dependencies (offline cache layer)
RUN ./mvnw dependency:go-offline -B || true

# Copy source code and package application
COPY src/ src/
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy built jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Dynamic port assignment for Render ($PORT)
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT}"]
