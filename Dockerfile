# Use the official OpenJDK 17 image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy the project files
COPY . .

# Build the application (skip tests to save time)
RUN ./mvnw clean package -DskipTests

# Expose the default Spring Boot port
EXPOSE 8080

# Run the Spring Boot jar
CMD ["java", "-jar", "target/furblur-0.0.1-SNAPSHOT.jar"]
