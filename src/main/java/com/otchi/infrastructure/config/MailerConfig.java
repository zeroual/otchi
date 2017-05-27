package com.otchi.infrastructure.config;

import com.otchi.application.MailService;
import com.otchi.infrastructure.mail.MailServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.spring4.SpringTemplateEngine;

@Configuration
public class MailerConfig {

    @Value("${otchi.mail.noReply}")
    private String noReplyMail;

    @Value("${otchi.url}")
    private String appUrl;

    @Bean
    public MailService mailService(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        return new MailServiceImpl(mailSender, templateEngine, noReplyMail, appUrl);
    }
}
