package com.otchi.infrastructure.notifications;

import com.otchi.domain.notifications.services.WebNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

public class WebsocketNotificationsPusher {

    private final SimpMessageSendingOperations messagingTemplate;

    private final String notificationsChannel;

    @Autowired
    public WebsocketNotificationsPusher(SimpMessageSendingOperations messagingTemplate, String notificationsChannel) {
        this.messagingTemplate = messagingTemplate;
        this.notificationsChannel = notificationsChannel;
    }

    private void sendMessage(String user, String destination, Object payload) {
        messagingTemplate.convertAndSendToUser(user, destination, payload);
    }

    public void pushNotification(WebNotification webNotification) {
        sendMessage(webNotification.getTo(), notificationsChannel, webNotification);
    }
}
