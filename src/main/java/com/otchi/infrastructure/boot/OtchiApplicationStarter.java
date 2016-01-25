package com.otchi.infrastructure.boot;

import com.otchi.infrastructure.config.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Configuration
@Import(value = ApplicationConfig.class)
public class OtchiApplicationStarter {

    public static void main(String[] args) {
        SpringApplication.run(OtchiApplicationStarter.class, args);
    }
}
