# Build stage
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copy Maven wrapper and POM from currency_conversion
COPY currency_conversion/.mvn/ ./currency_conversion/.mvn/
COPY currency_conversion/mvnw currency_conversion/pom.xml ./currency_conversion/
RUN sed -i 's/\r$//' ./currency_conversion/mvnw && chmod +x ./currency_conversion/mvnw

# Copy source and build package
COPY currency_conversion/src/ ./currency_conversion/src/
WORKDIR /app/currency_conversion
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/currency_conversion/target/*.jar app.jar

ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT}"]
