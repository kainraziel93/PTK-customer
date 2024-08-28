FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/customer-service*.jar /app/customer-service.jar
CMD ["java", "-jar", "customer-service.jar"]