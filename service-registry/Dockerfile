FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "app.jar"]
