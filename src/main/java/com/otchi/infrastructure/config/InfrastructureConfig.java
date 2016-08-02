package com.otchi.infrastructure.config;

import com.otchi.infrastructure.config.database.DatabaseConfig;
import com.otchi.infrastructure.config.storage.BlobStorageConfig;
import com.otchi.infrastructure.utils.FileUtilsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({SecurityConfig.class, DatabaseConfig.class, SocialConfig.class, BlobStorageConfig.class,
        ThymeleafConfig.class, MailerConfig.class})
public class InfrastructureConfig {

    @Bean
    public FileUtilsServiceImpl fileUtilsService() {
        return new FileUtilsServiceImpl();
    }
}
