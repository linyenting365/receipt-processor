# Stage 1: Build the application using Maven with Java 17
FROM maven:3.8.4-openjdk-17 as builder
WORKDIR /app
COPY . /app
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the final Docker image using Java 17
FROM openjdk:17-oracle
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
