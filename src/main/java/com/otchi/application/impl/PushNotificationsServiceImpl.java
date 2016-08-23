package com.otchi.application.impl;

import com.otchi.application.PushNotificationsService;
import com.otchi.application.utils.DateFactory;
import com.otchi.domain.social.models.Notification;
import com.otchi.domain.social.models.NotificationType;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.social.repositories.NotificationsRepository;
import com.otchi.infrastructure.notifications.WebsocketMessageSending;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PushNotificationsServiceImpl implements PushNotificationsService {

    private WebsocketMessageSending websocketMessageSending;
    private NotificationsRepository notificationsRepository;
    private DateFactory dateFactory;

    @Autowired
    public PushNotificationsServiceImpl(WebsocketMessageSending websocketMessageSending,
                                        NotificationsRepository notificationsRepository,
                                        DateFactory dateFactory) {
        this.websocketMessageSending = websocketMessageSending;
        this.notificationsRepository = notificationsRepository;
        this.dateFactory = dateFactory;
    }

    @Override
    public Notification sendLikeNotificationToPostAuthor(Post post, String likerUsername) {
        String postAuthor = post.getAuthor().getUsername();
        Notification notification = new Notification(postAuthor, likerUsername, post.getId(), NotificationType.LIKED);
        Date now = getCurrentDate();
        notification.changeCreationDateTo(now);
        notificationsRepository.save(notification);
        websocketMessageSending.sendLikedEvent(postAuthor, notification);
        return notification;
    }

    private Date getCurrentDate() {
        return dateFactory.now();
    }


}
