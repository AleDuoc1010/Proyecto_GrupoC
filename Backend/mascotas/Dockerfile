# Usamos Java 22 para que coincida con tu pom.xml
FROM eclipse-temurin:22-jre

WORKDIR /app

# Usamos los comandos estándar de Linux (groupadd y useradd) en lugar de addgroup
RUN groupadd -r spring && useradd -r -g spring spring
USER spring

# El nombre del JAR debe coincidir con el artifactId y version de tu pom.xml
COPY target/mascotas-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]