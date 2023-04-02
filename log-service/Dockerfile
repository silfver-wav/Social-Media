FROM openjdk:17-jdk-slim
ADD target/log-service.jar log-service.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","log-service.jar"]