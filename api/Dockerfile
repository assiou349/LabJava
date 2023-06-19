FROM eclipse-temurin:17-jdk-jammy

RUN mkdir -p /usr/src/core
ADD target/api-0.0.1-SNAPSHOT.jar /usr/src/core

WORKDIR /usr/src/core


ENTRYPOINT ["java", "-jar","api-0.0.1-SNAPSHOT.jar"]
