# 💱 Currency Converter API

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)

A highly available currency conversion backend that wraps a third-party exchange rate provider to offer reliable and validated currency conversion endpoints.

## 🚀 Overview

When relying on third-party APIs for critical data (like live exchange rates), your system is only as reliable as the provider. If the provider goes down, your service shouldn't crash. 

This REST API is designed with resiliency in mind. It wraps the upstream exchange rate provider with strict input validation and graceful error fallback mechanisms, ensuring the API degrades safely and provides clear error messages rather than unhandled exceptions.

## ✨ Key Features

- **Graceful Fallbacks:** Implements fallback mechanisms and safe error handling for upstream provider timeouts and failures.
- **Strict Validation:** Validates ISO currency codes and conversion amounts before making external network calls, saving bandwidth and preventing bad requests.
- **RESTful Design:** Exposes clean, stateless endpoints for easy integration by frontend clients.

## 🏗️ Tech Stack
- **Language:** Java 17+
- **Framework:** Spring Boot 3
- **Concepts:** REST API Design, Error Handling, System Architecture

## ⚙️ Local Setup

1. **Clone the repository:**
   ```bash
   git clone https://github.com/dhiva2167/currency-conversion.git
   cd currency-conversion
   ```

2. **Configure API Keys:**
   Add your third-party exchange rate API key to `application.properties`:
   ```properties
   exchange.api.key=your_api_key_here
   ```

3. **Run the Application:**
   ```bash
   ./mvnw spring-boot:run
   ```

## 📡 API Endpoints (Example)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/v1/convert?from=USD&to=EUR&amount=100` | Converts a specified amount from one currency to another. |
| `GET` | `/api/v1/rates?base=USD` | Fetches the latest exchange rates for a base currency. |
