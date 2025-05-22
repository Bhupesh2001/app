FROM amazoncorretto:17.0.14

COPY target/app-0.0.2-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]