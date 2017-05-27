package com.otchi.domain.notifications.services;

import com.otchi.api.facades.dto.UserDTO;
import com.otchi.application.UserService;
import com.otchi.domain.notifications.models.Notification;
import com.otchi.domain.users.models.User;
import com.otchi.infrastructure.notifications.WebsocketNotificationsPusher;
import org.junit.Test;

import java.time.LocalDateTime;

import static com.otchi.domain.notifications.models.NotificationType.LIKED;
import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Optional.of;
import static org.mockito.Mockito.*;

public class RealTimeNotifierTest {

    private WebsocketNotificationsPusher websocketNotificationsPusher = mock(WebsocketNotificationsPusher.class);
    private UserService userService = mock(UserService.class);
    private RealTimeNotifier realTimeNotifier = new RealTimeNotifier(websocketNotificationsPusher,userService);
    private LocalDateTime notificationDate = parse("2017-07-13 06:48:21", ofPattern("yyyy-MM-dd HH:mm:ss"));

    @Test
    public void shouldPushWebSocketNotification() {
        Notification notification = new Notification("receiver", "sender", 22L, LIKED);
        notification.changeCreationDateTo(notificationDate);

        User sender = new User("sender");
        when(userService.findUserByUsername("sender")).thenReturn(of(sender));

        realTimeNotifier.pushNotification(notification);

        WebNotification webNotification = new WebNotification();
        webNotification.setPostId(22L);
        webNotification.setCreatedAt(notificationDate);
        webNotification.setType(LIKED);
        webNotification.setTo("receiver");


        webNotification.setSender(new UserDTO(sender));
        verify(websocketNotificationsPusher).pushNotification(webNotification);
    }

}