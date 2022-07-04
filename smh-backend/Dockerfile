FROM openjdk:16-jdk-alpine

VOLUME /tmp
COPY ./smh-backend.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
