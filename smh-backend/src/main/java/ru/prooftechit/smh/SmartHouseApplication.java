package ru.prooftechit.smh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * @author Roman Zdoronok
 */
@SpringBootApplication
@ConfigurationPropertiesScan("ru.prooftechit.smh.configuration.properties")
public class SmartHouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartHouseApplication.class, args);
    }

}
