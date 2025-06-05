FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy gradle files
COPY build.gradle settings.gradle gradlew ./
COPY gradle gradle

# Copy source code
COPY src src

# Build the application
RUN chmod +x ./gradlew
RUN ./gradlew build -x test

# Run the application
EXPOSE 8080
CMD ["java", "-jar", "build/libs/*.jar"]