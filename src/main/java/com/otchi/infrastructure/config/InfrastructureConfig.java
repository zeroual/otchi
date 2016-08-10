package com.otchi.infrastructure.config;

import com.otchi.application.NotificationsService;
import com.otchi.infrastructure.config.database.DatabaseConfig;
import com.otchi.infrastructure.config.storage.BlobStorageConfig;
import com.otchi.infrastructure.notifications.NotificationsServiceImpl;
import com.otchi.infrastructure.notifications.WebsocketMessageSending;
import com.otchi.infrastructure.utils.FileUtilsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({SecurityConfig.class, DatabaseConfig.class, SocialConfig.class, BlobStorageConfig.class,
        ThymeleafConfig.class, MailerConfig.class,WebsocketConfig.class})
@ComponentScan({"com.otchi.api"})
public class InfrastructureConfig {

    @Bean
    public FileUtilsServiceImpl fileUtilsService() {
        return new FileUtilsServiceImpl();
    }

    @Bean
    public NotificationsService notificationsService(WebsocketMessageSending websocketMessageSending) {
        return new NotificationsServiceImpl(websocketMessageSending);
    }
}
