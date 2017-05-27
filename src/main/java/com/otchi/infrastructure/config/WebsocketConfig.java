package com.otchi.infrastructure.config;


import com.otchi.application.ConnectedUsersService;
import com.otchi.infrastructure.notifications.WebSocketConnectedUsersService;
import com.otchi.infrastructure.notifications.WebsocketNotificationsPusher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.messaging.DefaultSimpUserRegistry;

@Configuration
@EnableWebSocketMessageBroker

public class WebsocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Value("${otchi.websocket.channel.notifications}")
    private String notificationsChannel;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socket").withSockJS();
    }

    @Bean
    public WebsocketNotificationsPusher websocketMessageSending(SimpMessageSendingOperations messagingTemplate) {
        return new WebsocketNotificationsPusher(messagingTemplate, notificationsChannel);
    }

    @Bean
    public DefaultSimpUserRegistry defaultSimpUserRegistry(){
        return new DefaultSimpUserRegistry();
    }
    @Bean
    public ConnectedUsersService connectedUsersService(DefaultSimpUserRegistry defaultSimpUserRegistry){
        return new WebSocketConnectedUsersService(defaultSimpUserRegistry);
    }

}
