package com.otchi.infrastructure.config;

import com.otchi.application.MailService;
import com.otchi.infrastructure.mail.MailServiceImpl;
import com.otchi.infrastructure.mail.MockMailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.spring4.SpringTemplateEngine;

import static com.otchi.infrastructure.config.Constants.SPRING_PROFILE_DEVELOPMENT;
import static com.otchi.infrastructure.config.Constants.SPRING_PROFILE_PRODUCTION;

@Configuration
public class MailerConfig {

    @Value("${otchi.mail.noReply}")
    private String noReplyMail;

    @Bean
    @Profile(SPRING_PROFILE_DEVELOPMENT)
    public MailService mailServiceMock() {
        return new MockMailService();
    }

    @Bean
    @Profile(SPRING_PROFILE_PRODUCTION)
    public MailService mailService(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        return new MailServiceImpl(mailSender, templateEngine, noReplyMail);
    }
}
