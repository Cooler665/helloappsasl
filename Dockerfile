FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/helloworld-1.0-SNAPSHOT.jar /app/helloworld.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/helloworld.jar"]





