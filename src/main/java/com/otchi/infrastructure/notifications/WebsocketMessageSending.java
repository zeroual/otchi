package com.otchi.infrastructure.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

public class WebsocketMessageSending {

    private final SimpMessageSendingOperations messagingTemplate;

    private final String postLikedEventDestination;

    @Autowired
    public WebsocketMessageSending(SimpMessageSendingOperations messagingTemplate, String postLikedEventDestination) {
        this.messagingTemplate = messagingTemplate;
        this.postLikedEventDestination = postLikedEventDestination;
    }

    public void sendLikedEvent(String user, Object likedEvent) {
        sendMessage(user, postLikedEventDestination, likedEvent);
    }

    private void sendMessage(String user, String destination, Object payload) {
        messagingTemplate.convertAndSendToUser(user, destination, payload);
    }
}
