package com.otchi.infrastructure.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

public class WebsocketMessageSending {

    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public WebsocketMessageSending(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendMessage(String user, String destination, Object payload) {
        messagingTemplate.convertAndSendToUser(user, destination, payload);
    }
}
