FROM maven:3-openjdk-15-slim

ADD . /lorvideos
WORKDIR /lorvideos

RUN ls -l

RUN mvn clean install

FROM maven:3-openjdk-15-slim

MAINTAINER Bruno Martins da Silva

VOLUME /tmp

COPY --from=0 "/lorvideos/backend/target/backend-1.0-SNAPSHOT.jar" app.jar

ENV JAVA_OPTS=""

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]