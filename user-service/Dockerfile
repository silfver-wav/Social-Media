FROM openjdk:17-jdk-slim
COPY target/user-service.jar user-service.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar","user-service.jar"]
