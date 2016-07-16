## Normal build (to be depricated)
### Build
```bash
$ ./gradlew build
```

### RethinkDB
```bash
$ ./rethinkdb --http-port 9090
```

### Run injest
```bash
$ java -jar build/libs/gs-rest-service-0.1.0.jar
```

##Docker build (incomplete)
###build the docker image with
```bash
$ ./gradlew build buildDocker
```

### run injest in the docker container with
```bash
$ docker run llay/gs-rest-service
```
## TODO
remove SpringApplication.run(Application.class, args)? from application.java main?
