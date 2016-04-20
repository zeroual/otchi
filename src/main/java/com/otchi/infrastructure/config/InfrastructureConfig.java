package com.otchi.infrastructure.config;

import com.otchi.application.MailService;
import com.otchi.infrastructure.mail.MailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({SecurityConfig.class, DatabaseConfig.class, SocialConfig.class})
public class InfrastructureConfig {

    @Bean
    public MailService mailService() {
        return new MailServiceImpl();
    }
}
