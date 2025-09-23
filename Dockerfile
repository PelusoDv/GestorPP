FROM amazoncorretto:11-alpine-jdk
MAINTAINER GestorPP
COPY target/GestorPP-0.0.1-SNAPSHOT.jar FinTrack.jar
ENTRYPOINT ["java","-jar","/FinTrack.jar"]
