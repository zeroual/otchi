package com.otchi.infrastructure.config;

import com.otchi.domain.events.DomainEvents;
import com.otchi.domain.events.EventsChannels;
import com.otchi.domain.events.PostCommentedEventHandler;
import com.otchi.infrastructure.config.database.DatabaseConfig;
import com.otchi.infrastructure.config.storage.BlobStorageConfig;
import com.otchi.infrastructure.eventBus.ReactorDomainEventsBus;
import com.otchi.infrastructure.utils.FileUtilsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import reactor.Environment;
import reactor.bus.EventBus;

import static reactor.bus.selector.Selectors.$;

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
    Environment env() {
        return Environment.initializeIfEmpty()
                .assignErrorJournal();
    }

    @Bean
    EventBus createEventBus(Environment env, PostCommentedEventHandler postCommentedEventHandler) {
        EventBus eventBus = EventBus.create(env, Environment.THREAD_POOL);
        eventBus.on($(EventsChannels.POST_COMMENTED), postCommentedEventHandler);
        return eventBus;
    }

    @Bean
    public DomainEvents domainEvents(EventBus eventBus) {
        return new ReactorDomainEventsBus(eventBus);
    }
}
