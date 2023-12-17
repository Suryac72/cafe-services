
#
# Build stage
#
FROM maven:3.6.0-jdk-17-slim AS build
COPY src /home/cafe-services/src
COPY pom.xml /home/cafe-services
RUN mvn -f /home/cafe-services/pom.xml clean package

#
# Package stage
#
FROM eclipse-temurin:17-jdk-alpine
COPY target/*.jar ecommerce-backend.jar
ENTRYPOINT ["java","-jar","/ecommerce-backend.jar"]
EXPOSE 8080
