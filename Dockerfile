# build target jar
FROM maven:3.6.3-jdk-8 as package

COPY . /usr/src/mymaven
WORKDIR /usr/src/mymaven
RUN mvn package

# build docker image
# server port is 20202, which is specifed in application.properties
FROM openjdk:8u242-jre-slim

COPY --from=package /usr/src/mymaven/target/be-0.0.1-SNAPSHOT.jar /usr/spme.jar
CMD java -jar /usr/spme.jar