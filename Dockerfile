FROM openjdk:11
WORKDIR /app
EXPOSE 8080
ADD target/rating_ms-0.0.1-SNAPSHOT.jar rating_ms-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar", "rating_ms-0.0.1-SNAPSHOT.jar"]
