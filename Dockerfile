# ================================
# 1. Build stage
# ================================
FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

# Install dependencies for Maven wrapper
RUN apk add --no-cache bash

# Copy Maven wrapper and project files
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Make mvnw executable
RUN chmod +x mvnw

# Build the application (skip tests for faster build)
RUN ./mvnw package -DskipTests


# ================================
# 2. Runtime stage
# ================================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy only the built jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (Render will actually use $PORT)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
