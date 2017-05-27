package com.otchi.domain.notifications.services;


import com.otchi.api.facades.dto.UserDTO;
import com.otchi.application.UserService;
import com.otchi.domain.notifications.models.Notification;
import com.otchi.domain.users.models.User;
import com.otchi.infrastructure.notifications.WebsocketNotificationsPusher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RealTimeNotifier {

    private final WebsocketNotificationsPusher websocketNotificationsPusher;
    private final UserService userService;

    @Autowired
    public RealTimeNotifier(WebsocketNotificationsPusher websocketNotificationsPusher, UserService userService) {

        this.websocketNotificationsPusher = websocketNotificationsPusher;
        this.userService = userService;
    }

    public void pushNotification(Notification notification) {
        WebNotification webNotification = new WebNotification();
        webNotification.setPostId(notification.postId());
        webNotification.setCreatedAt(notification.getCreationDate());
        webNotification.setType(notification.getType());
        webNotification.setTo(notification.getUsername());

        User user = userService.findUserByUsername(notification.senderId()).get();
        webNotification.setSender(new UserDTO(user));

        websocketNotificationsPusher.pushNotification(webNotification);
    }
}
