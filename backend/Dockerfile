FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy waiting script
COPY wait-for-postgres.sh /wait-for-postgres.sh
RUN chmod +x /wait-for-postgres.sh

COPY target/*.jar app.jar

EXPOSE 8080

CMD ["/bin/sh", "-c", "/wait-for-postgres.sh postgres java -jar app.jar"]