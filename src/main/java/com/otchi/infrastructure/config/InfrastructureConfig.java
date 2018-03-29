package com.otchi.infrastructure.config;

import com.google.common.eventbus.EventBus;
import com.otchi.domain.analytics.events.PostDeletedEventHandler;
import com.otchi.domain.notifications.events.CommentedNotificationEventHandler;
import com.otchi.domain.notifications.events.DomainEvents;
import com.otchi.domain.notifications.events.LikeNotificationEventHandler;
import com.otchi.infrastructure.config.database.DatabaseConfig;
import com.otchi.infrastructure.config.storage.BlobStorageConfig;
import com.otchi.infrastructure.event.bus.GuavaDomainEventsBus;
import com.otchi.infrastructure.utils.FileUtilsServiceImpl;
import com.otchi.infrastructure.web.WebConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;


@Configuration
@Import({WebConfigurer.class, SecurityConfig.class, DatabaseConfig.class, SocialConfig.class, BlobStorageConfig.class,
        ThymeleafConfig.class, MailerConfig.class, WebsocketConfig.class, JacksonConfig.class})
@ComponentScan({"com.otchi.api"})
@EnableScheduling
public class InfrastructureConfig {

    @Bean
    public FileUtilsServiceImpl fileUtilsService() {
        return new FileUtilsServiceImpl();
    }


    @Bean
    EventBus createEventBus(CommentedNotificationEventHandler commentedNotificationEventHandler,
                            LikeNotificationEventHandler likeNotificationEventHandler,
                            PostDeletedEventHandler postDeletedEventHandler) {
        EventBus eventBus = new EventBus();
        eventBus.register(commentedNotificationEventHandler);
        eventBus.register(likeNotificationEventHandler);
        eventBus.register(postDeletedEventHandler);
        return eventBus;
    }

    @Bean
    public DomainEvents domainEvents(EventBus eventBus) {
        return new GuavaDomainEventsBus(eventBus);
    }
}
