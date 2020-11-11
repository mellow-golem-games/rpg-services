FROM openjdk:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/rpg-services-0.0.1-SNAPSHOT-standalone.jar /rpg-services/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/rpg-services/app.jar"]
