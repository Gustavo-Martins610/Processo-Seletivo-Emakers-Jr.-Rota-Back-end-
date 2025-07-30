FROM openjdk:17-jdk-alpine
LABEL maintainer="gustavo981233@gmail.com"
WORKDIR /api-biblioteca
COPY target/api-biblioteca-0.0.1-SNAPSHOT.jar api-biblioteca.jar
ENTRYPOINT ["java", "-jar", "api-biblioteca.jar"]