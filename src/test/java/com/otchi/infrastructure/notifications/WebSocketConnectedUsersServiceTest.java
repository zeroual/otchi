package com.otchi.infrastructure.notifications;

import org.junit.Test;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.web.socket.messaging.DefaultSimpUserRegistry;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WebSocketConnectedUsersServiceTest {


    private DefaultSimpUserRegistry defaultSimpUserRegistry = mock(DefaultSimpUserRegistry.class);
    private WebSocketConnectedUsersService webSocketConnectedUsersService = new WebSocketConnectedUsersService(defaultSimpUserRegistry);

    @Test
    public void shouldReturnFalseIfUserIsNotConnectedWithWebSocket() {

        when(defaultSimpUserRegistry.getUser("user")).thenReturn(null);
        boolean isConnected = webSocketConnectedUsersService.isConnected("user");
        assertThat(isConnected).isFalse();
    }

    @Test
    public void shouldReturnTrueIfUserIsNotConnectedWithWebSocket() {
        SimpUser simpUser = mock(SimpUser.class);
        when(defaultSimpUserRegistry.getUser("user")).thenReturn(simpUser);
        boolean isConnected = webSocketConnectedUsersService.isConnected("user");
        assertThat(isConnected).isTrue();
    }

}