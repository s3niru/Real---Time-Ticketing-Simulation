package com.spring.springoopcw;

import com.spring.springoopcw.Logs.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringOopcwApplication {

    public static void main(String[] args) {
        Logger logger = new Logger();
        logger.deleteFiles();
        SpringApplication.run(SpringOopcwApplication.class, args);
    }
// Database url = http://localhost:8080/h2-console
}
