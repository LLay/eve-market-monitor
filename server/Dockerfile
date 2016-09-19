# Taken from https://spring.io/guides/gs/spring-boot-docker/
FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD build/libs/emm-server-0.1.0.jar /opt/lib/emm-server-0.1.0.jar
RUN sh -c 'touch /opt/lib/emm-server-0.1.0.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","opt/lib/emm-server-0.1.0.jar"]
# XXX Where are the exposed ports?
