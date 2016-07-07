package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        Poller poller = new Poller(1000, 10000, "http://api.eve-central.com/api/marketstat?typeid=34&typeid=35&regionlimit=10000002");
        poller.poll();

    }
}