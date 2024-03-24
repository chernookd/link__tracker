package edu.java;

import edu.java.configuration.ApplicationConfig;
import edu.java.configuration.JdbcConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableConfigurationProperties({ApplicationConfig.class, JdbcConfig.class})
@EnableScheduling
@SpringBootApplication
public class ScrapperApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScrapperApplication.class, args);
    }
}
