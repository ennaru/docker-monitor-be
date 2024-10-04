package com.ennaru.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class DockerMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(DockerMonitorApplication.class, args);
    }

}
