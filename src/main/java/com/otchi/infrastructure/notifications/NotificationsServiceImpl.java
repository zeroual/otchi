package com.otchi.infrastructure.notifications;

import com.otchi.application.NotificationsService;
import com.otchi.domain.social.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationsServiceImpl implements NotificationsService {

    private WebsocketMessageSending websocketMessageSending;

    @Autowired
    public NotificationsServiceImpl(WebsocketMessageSending websocketMessageSending) {
        this.websocketMessageSending = websocketMessageSending;
    }

    @Override
    public void sendLikeNotificationToPostAuthor(Post post, String likerUsername) {
        websocketMessageSending.sendMessage(post.getAuthor().getUsername(), "/topic/notifications", "liked");
    }
}
