package com.otchi.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({WebConfig.class, ServicesConfig.class, InfrastructureConfig.class})
public class ApplicationConfig {
}
