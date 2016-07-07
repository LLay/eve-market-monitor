## Normal build (to be depricated)
###build with
```bash
$ ./gradlew build
```

###run injest with
```bash
$ java -jar build/libs/gs-rest-service-0.1.0.jar
```

##Docker build
###build the docker image with
```bash
$ ./gradlew build buildDocker
```

### run injest in the docker container with
```bash
$ docker run llay/gs-rest-service
```
## TODO
remokve SpringApplication.run(Application.class, args)? from application.java main?
