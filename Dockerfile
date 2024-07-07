FROM amazoncorretto:21-alpine-jdk

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} application.jar

ENTRYPOINT ["java", "-jar", "/application.jar"]