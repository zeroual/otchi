package com.otchi.config;

import com.otchi.application.utils.DateFactory;
import com.otchi.utils.mocks.MockDateFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TestConfiguration {


    @Bean
    @Primary
    public DateFactory dateFactory() {
        return new MockDateFactory();
    }
}

