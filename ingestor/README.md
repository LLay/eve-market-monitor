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
$ docker run -d -P --name rethink1 rethinkdb
```

### run injest in the docker container with
```bash
$ docker run -p 9090:9090 -p 28015:28015 llay/emm-ingestor
```

###
Rethink database ui available at http://localhost:9090/#dashboard

### Clean up
```bash
$ docker stop llay/emm-ingestor
$ docker rm llay/emm-ingestor
$ docker rmi llay/emm-ingestor
```

## TODO
remove SpringApplication.run(Application.class, args)? from application.java main?
