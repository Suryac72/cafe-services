
#
# Build stage
#
FROM maven:3.3-jdk-8 AS build
COPY src /home/cafe-services/src
COPY pom.xml /home/cafe-services
RUN mvn -f /home/cafe-services/pom.xml clean package

#
# Package stage
#
FROM eclipse-temurin:17-jdk-alpine
COPY --from=build /home/cafe-services/target/ecommerce-backend-0.0.1-SNAPSHOT.jar /usr/local/lib/ecommerce-backend.jar
ENTRYPOINT ["java","-jar","/ecommerce-backend.jar"]
EXPOSE 8080
