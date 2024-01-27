# Use a base image with Java 17
FROM openjdk:17-oracle

# Argument to pass the jar file name
ARG JAR_FILE=target/*.jar

# Copy the application's jar file to the container
COPY ${JAR_FILE} app.jar

# Set the container to execute the application
ENTRYPOINT ["java","-jar","/app.jar"]