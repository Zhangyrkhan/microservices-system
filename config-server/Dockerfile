FROM eclipse-temurin:21-jdk
VOLUME /tmp
ARG JAR_FILE=target/config-server-1.0.0.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]