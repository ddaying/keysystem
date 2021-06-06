FROM openjdk:8

LABEL maintainer="ddaying@naver.com"

VOLUME /tmp

EXPOSE 8080

RUN mkdir /app

COPY build/libs/keysystem-0.0.1-SNAPSHOT.jar /app/api.jar

ENTRYPOINT [ "java", "-jar", "/app/api.jar"]
