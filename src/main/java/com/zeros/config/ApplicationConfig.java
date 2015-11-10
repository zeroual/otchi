package com.zeros.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ApiConfig.class,DomainConfig.class})
public class ApplicationConfig {
}
