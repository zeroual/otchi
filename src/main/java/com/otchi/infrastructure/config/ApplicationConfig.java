package com.otchi.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({WebConfig.class, DatabaseConfig.class, ServicesConfig.class, SecurityConfig.class, MongoConfig.class})
public class ApplicationConfig {
}
