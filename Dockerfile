# FROM openjdk:11-slim
# EXPOSE 8080
# ARG JAR_FILE
# COPY ${JAR_FILE} app.jar
# LABEL authors="jks83"
# ENTRYPOINT ["java", "-jar", "/app.jar"]
FROM gradle:jdk11-alpine as builder
WORKDIR /workspace/app
COPY . /workspace/app/
RUN ./gradlew build -p ${MODULE}
FROM openjdk:11-jre-slim

RUN groupadd -r appuser && useradd -r -g appuser appuser

COPY --from=builder /workspace/app/${MODULE}/build/libs/${MODULE}.jar ./${MODULE}.jar
EXPOSE 8080

USER appuser

ENTRYPOINT ["java","-jar","${MODULE}.jar"]