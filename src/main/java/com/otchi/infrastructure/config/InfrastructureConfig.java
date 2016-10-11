package com.otchi.infrastructure.config;

import com.google.common.eventbus.EventBus;
import com.otchi.domain.events.DomainEvents;
import com.otchi.domain.events.PostCommentedEventHandler;
import com.otchi.infrastructure.config.database.DatabaseConfig;
import com.otchi.infrastructure.config.storage.BlobStorageConfig;
import com.otchi.infrastructure.eventBus.GuavaDomainEventsBus;
import com.otchi.infrastructure.utils.FileUtilsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({SecurityConfig.class, DatabaseConfig.class, SocialConfig.class, BlobStorageConfig.class,
        ThymeleafConfig.class, MailerConfig.class, WebsocketConfig.class})
@ComponentScan({"com.otchi.api"})

public class InfrastructureConfig {

    @Bean
    public FileUtilsServiceImpl fileUtilsService() {
        return new FileUtilsServiceImpl();
    }


    @Bean
    EventBus createEventBus(PostCommentedEventHandler postCommentedEventHandler) {
        EventBus eventBus = new EventBus();
        eventBus.register(postCommentedEventHandler);
        return eventBus;
    }

    @Bean
    public DomainEvents domainEvents(EventBus eventBus) {
        return new GuavaDomainEventsBus(eventBus);
    }
}
