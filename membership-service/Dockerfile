FROM openjdk:11-slim
EXPOSE 8080
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
LABEL authors="jks83"

ENTRYPOINT ["java", "-jar", "/app.jar"]
