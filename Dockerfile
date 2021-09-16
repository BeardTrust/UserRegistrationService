FROM openjdk:11
MAINTAINER Matthew.Crowell@Smoothstack.com
RUN adduser --system --group discoveryservice
USER discoveryservice:discoveryservice
COPY target/userservice-0.0.1-SNAPSHOT.jar userservice.jar
ENTRYPOINT ["java", "-jar", "userservice.jar", "--spring.profiles.active=dev"]
