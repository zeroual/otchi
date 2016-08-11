package com.otchi.infrastructure.config.websocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import static com.otchi.infrastructure.config.Constants.SPRING_PROFILE_DEVELOPMENT;


@Configuration
@EnableWebSocketMessageBroker
@Profile(SPRING_PROFILE_DEVELOPMENT)
public class WebsocketDevConfig extends AbstractWebSocketMessageBrokerConfigurer {


    @Value("${otchi.stomp.relayDestinationPrefix:/topic}")
    private String relayDestinationPrefix;

    @Value("${otchi.stomp.appDestinationPrefix:/app}")
    private String appDestinationPrefix;

    @Value("${otchi.stomp.endpoint:/socket}")
    private String endpoint;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(relayDestinationPrefix);
        registry.setApplicationDestinationPrefixes(appDestinationPrefix);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(endpoint).withSockJS();
    }
}
