FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar ecommerce-backend.jar
ENTRYPOINT ["java","-jar","/ecommerce-backend.jar"]
EXPOSE 8080