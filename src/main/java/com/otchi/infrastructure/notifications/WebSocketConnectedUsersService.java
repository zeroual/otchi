package com.otchi.infrastructure.notifications;

import com.otchi.application.ConnectedUsersService;
import org.springframework.web.socket.messaging.DefaultSimpUserRegistry;

public class WebSocketConnectedUsersService implements ConnectedUsersService {

    private final DefaultSimpUserRegistry defaultSimpUserRegistry;

    public WebSocketConnectedUsersService(DefaultSimpUserRegistry defaultSimpUserRegistry) {

        this.defaultSimpUserRegistry = defaultSimpUserRegistry;
    }

    @Override
    public boolean isConnected(String username) {
        return defaultSimpUserRegistry.getUser(username) != null ? true : false;
    }
}
