FROM eclipse-temurin:21

LABEL authors="MUHAMMED"

WORKDIR /app

COPY target/biletbul-0.0.1-SNAPSHOT.jar /app/biletbul-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "biletbul-0.0.1-SNAPSHOT.jar"]