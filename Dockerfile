FROM openjdk:23-jdk
LABEL authors="Adriano"

WORKDIR /app

COPY target/ecommerce-erp-back-end.jar /app/ecommerce-erp-back-end.jar

ENTRYPOINT ["java", "-jar", "ecommerce-erp-back-end.jar"]