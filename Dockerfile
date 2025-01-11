FROM openjdk:11-jre-slim
VOLUME /tmp
COPY target/employee-project-service.jar employee-project-service.jar
ENTRYPOINT ["java", "-jar", "/employee-project-service.jar"]