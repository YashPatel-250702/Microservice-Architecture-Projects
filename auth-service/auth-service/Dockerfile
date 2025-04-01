FROM openjdk:17-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} auth-service.jar
ENTRYPOINT ["java", "-jar", "auth-service.jar"]
EXPOSE 9091
