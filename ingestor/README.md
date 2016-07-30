## Normal build (to be depricated)
### Build
```bash
$ ./gradlew build
```

### RethinkDB
```bash
$ rethinkdb --http-port 9090
```

### Run injest
```bash
$ java -jar build/libs/emm-ingestor-0.1.0.jar
```

##Docker build (incomplete)
###build the docker image with
```bash
$ ./gradlew build buildDocker
```

### RethinkDB
```bash
$ rethinkdb --http-port 9090
```

### run injest in the docker container with
```bash
$ docker run llay/emm-ingestor
```

###
Rethink database ui available at http://localhost:9090/#dashboard

## TODO
remove SpringApplication.run(Application.class, args)? from application.java main?
