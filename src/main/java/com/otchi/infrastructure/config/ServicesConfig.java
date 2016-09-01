package com.otchi.infrastructure.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.otchi.application", "com.otchi.domain"})
public class ServicesConfig {

}
