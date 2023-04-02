FROM openjdk:17-jdk-slim
ADD target/chat-service.jar chat-service.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","chat-service.jar"]