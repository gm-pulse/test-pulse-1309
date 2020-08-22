FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/checkout-0.0.1-SNAPSHOT.jar checkout.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/checkout.jar"]