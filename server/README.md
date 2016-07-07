## Normal build (to be depricated)
### Build
```bash
$ ./gradlew build
```

### Run server on http://localhost:8080
```bash
$ java -jar build/libs/gs-rest-service-0.1.0.jar
```

##Docker build
### Build the docker image
```bash
$ ./gradlew build buildDocker
```

### Run the docker container
```bash
$ docker run -p 8080:8080 -t llay/gs-rest-service
```

### access the server
access at `http://localhost:8080`

## Example endpoints
`http://localhost:8080/marketstat`
