# Dockerfile for Java 24 Spring Boot + Maven

# Use a custom base image with Java 24 support (Temurin Nightly or Adoptium)
FROM eclipse-temurin:24-jdk

# Set working directory
WORKDIR /app

# Copy project files into the image
COPY . .

# Grant permission to Maven wrapper
RUN chmod +x ./mvnw

# Build the project (skip tests for faster build)
RUN ./mvnw clean install -DskipTests

# Expose port 8080
EXPOSE 8080

# Run the application
CMD ["./mvnw", "spring-boot:run"]
