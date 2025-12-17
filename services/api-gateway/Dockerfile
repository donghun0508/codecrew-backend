FROM eclipse-temurin:25-jre
WORKDIR /app

COPY services/api-gateway/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
