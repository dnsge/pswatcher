FROM openjdk:11
COPY target/pswatcher-full.jar /app/pswatcher.jar

ENTRYPOINT ["java", "-jar", "/app/pswatcher.jar"]