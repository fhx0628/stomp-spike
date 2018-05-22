FROM openjdk:8-jdk-alpine
ENV PORT=8090
COPY ws-0.0.1.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Dserver.port=$PORT","-jar","/app.jar"]