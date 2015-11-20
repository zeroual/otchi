package com.zeros.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({WebConfig.class, DomainConfig.class})
public class ApplicationConfig {
}
