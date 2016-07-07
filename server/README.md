## Normal build (to be depricated)
###build with
```bash
$ ./gradlew build
```

###run server on http://localhost:8080 with
```bash
$ java -jar build/libs/gs-rest-service-0.1.0.jar
```

##Docker build
#### (Incomplete)
###build the docker image with
```bash
$ ./gradlew build buildDocker
```

### run the docker image with
```bash
$ docker run llay/gs-rest-service
```

### access the server (Not working)
access at http://localhost:8080
