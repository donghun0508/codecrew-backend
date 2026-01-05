FROM eclipse-temurin:25-jre
WORKDIR /app

COPY services/codecrew-service-world-master/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
