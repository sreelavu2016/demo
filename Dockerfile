FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} DevOpsTraining.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/application.jar"]