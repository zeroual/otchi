package com.otchi.domain.notifications.events;

import com.google.common.eventbus.Subscribe;
import com.otchi.domain.notifications.models.Notification;
import com.otchi.domain.notifications.services.NotificationSaver;
import com.otchi.domain.notifications.services.NotifierService;
import com.otchi.domain.social.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeNotificationEventHandler {

    private final NotifierService notifierService;
    private final NotificationSaver notificationSaver;

    @Autowired
    public LikeNotificationEventHandler(NotifierService notifierService, NotificationSaver notificationSaver) {
        this.notifierService = notifierService;
        this.notificationSaver = notificationSaver;
    }

    @Subscribe
    public void sendLikeNotificationToPostAuthor(LikePostEvent likePostEvent) {

        Post likedPost = likePostEvent.getLikedPost();
        String likeOwner = likePostEvent.getLikeOwner();

        if (!likedPost.isOwnedBy(likeOwner)) {
            Notification notification = notificationSaver.createLikeNotificationFrom(likePostEvent);
            notifierService.sendNotification(notification);
        }
    }

}
