package com.otchi.infrastructure.config;


import com.otchi.infrastructure.notifications.WebsocketMessageSending;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker

public class WebsocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Value("${otchi.websocket.events.liked}")
    private String postLikedEventDestination;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socket").withSockJS();
    }

    @Bean
    public WebsocketMessageSending websocketMessageSending(SimpMessageSendingOperations messagingTemplate) {
        return new WebsocketMessageSending(messagingTemplate, postLikedEventDestination);
    }


}
