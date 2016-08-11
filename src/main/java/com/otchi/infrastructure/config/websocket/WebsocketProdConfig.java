package com.otchi.infrastructure.config.websocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import static com.otchi.infrastructure.config.Constants.SPRING_PROFILE_PRODUCTION;


@Configuration
@EnableWebSocketMessageBroker
@Profile(SPRING_PROFILE_PRODUCTION)
public class WebsocketProdConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Value("${otchi.stomp.relayHost}")
    private String relayHost;

    @Value("${otchi.stomp.relayPort:61613}")
    private int relayPort;

    @Value("${otchi.stomp.clientLogin:guest}")
    private String clientLogin;

    @Value("${otchi.stomp.clientPasscode:guest}")
    private String clientPasscode;

    @Value("${otchi.stomp.systemLogin:guest}")
    private String systemLogin;

    @Value("${otchi.stomp.systemPasscode:guest}")
    private String systemPasscode;

    @Value("${otchi.stomp.virtualHost}")
    private String virtualHost;


    @Value("${otchi.stomp.relayDestinationPrefix:/topic}")
    private String relayDestinationPrefix;

    @Value("${otchi.stomp.appDestinationPrefix:/app}")
    private String appDestinationPrefix;

    @Value("${otchi.stomp.endpoint:/socket}")
    private String endpoint;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        registry.enableStompBrokerRelay(relayDestinationPrefix)
                .setRelayHost(relayHost)
                .setVirtualHost(virtualHost)
                .setClientLogin(clientLogin)
                .setClientPasscode(clientPasscode)
                .setSystemLogin(systemLogin)
                .setSystemPasscode(systemPasscode);
        registry.setApplicationDestinationPrefixes(appDestinationPrefix);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(endpoint).withSockJS();
    }
}
