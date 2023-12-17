FROM eclipse-temurin:17-jdk-alpine
COPY target/*.jar ecommerce-backend.jar
ENTRYPOINT ["java","-jar","/ecommerce-backend.jar"]
EXPOSE 8080
