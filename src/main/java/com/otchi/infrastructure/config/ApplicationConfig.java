package com.otchi.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({WebConfig.class, DomainConfig.class, ServicesConfig.class})
public class ApplicationConfig {
}
