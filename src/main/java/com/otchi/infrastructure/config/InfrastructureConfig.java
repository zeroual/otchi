package com.otchi.infrastructure.config;

import com.google.common.eventbus.EventBus;
import com.otchi.domain.events.DomainEvents;
import com.otchi.domain.events.PushNotificationEventHandler;
import com.otchi.domain.events.RecipePostedEventHandler;
import com.otchi.infrastructure.config.database.DatabaseConfig;
import com.otchi.infrastructure.config.elasticsearch.ElasticsearchConfig;
import com.otchi.infrastructure.config.storage.BlobStorageConfig;
import com.otchi.infrastructure.eventBus.GuavaDomainEventsBus;
import com.otchi.infrastructure.utils.FileUtilsServiceImpl;
import com.otchi.infrastructure.web.WebConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({WebConfigurer.class, SecurityConfig.class, DatabaseConfig.class, SocialConfig.class, BlobStorageConfig.class,
        ThymeleafConfig.class, MailerConfig.class, WebsocketConfig.class, JacksonConfig.class, ElasticsearchConfig.class})
@ComponentScan({"com.otchi.api"})

public class InfrastructureConfig {

    @Bean
    public FileUtilsServiceImpl fileUtilsService() {
        return new FileUtilsServiceImpl();
    }


    @Bean
    EventBus createEventBus(PushNotificationEventHandler pushNotificationEventHandler,
                            RecipePostedEventHandler recipePostedEventHandler) {
        EventBus eventBus = new EventBus();
        eventBus.register(pushNotificationEventHandler);
        eventBus.register(recipePostedEventHandler);
        return eventBus;
    }

    @Bean
    public DomainEvents domainEvents(EventBus eventBus) {
        return new GuavaDomainEventsBus(eventBus);
    }
}
