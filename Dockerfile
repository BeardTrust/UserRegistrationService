FROM openjdk:11
LABEL maintainer="Matthew.Crowell@Smoothstack.com"
ADD target/userservice-0.0.1-SNAPSHOT.jar userservice.jar
ENTRYPOINT ["java", "-jar", "userservice.jar"]