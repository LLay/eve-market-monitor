# Taken from https://spring.io/guides/gs/spring-boot-docker/
FROM frolvlad/alpine-oraclejdk8:slim

VOLUME /tmp

ADD emm-ingestor-0.1.0.jar opt/lib/emm-ingestor-0.1.0.jar

RUN sh -c 'touch /opt/lib/emm-ingestor-0.1.0.jar'
EXPOSE 9090
EXPOSE 28015
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/lib/emm-ingestor-0.1.0.jar"]
