package com.otchi.infrastructure.config;

import com.otchi.application.MailService;
import com.otchi.infrastructure.config.database.DatabaseConfig;
import com.otchi.infrastructure.config.storage.BlobStorageConfig;
import com.otchi.infrastructure.mail.MailServiceImpl;
import com.otchi.infrastructure.utils.FileUtilsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({SecurityConfig.class, DatabaseConfig.class, SocialConfig.class, BlobStorageConfig.class})
public class InfrastructureConfig {

    @Bean
    public MailService mailService() {
        return new MailServiceImpl();
    }

    @Bean
    public FileUtilsServiceImpl fileUtilsService() {
        return new FileUtilsServiceImpl();
    }
}
