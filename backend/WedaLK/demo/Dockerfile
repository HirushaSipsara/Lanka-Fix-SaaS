# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml to cache dependency downloads
COPY pom.xml .

# Go offline to download and cache dependencies
RUN mvn dependency:go-offline -B

# Copy src directory and build package
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the built jar file from build stage
COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar app.jar

# Create uploads directory as specified in application.properties
RUN mkdir -p uploads

# Expose backend port
EXPOSE 8081

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]
