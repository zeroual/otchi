package com.otchi.domain.notifications.events;

import com.google.common.eventbus.Subscribe;
import com.otchi.domain.notifications.models.Notification;
import com.otchi.domain.notifications.services.NotificationSaver;
import com.otchi.domain.notifications.services.NotifierService;
import com.otchi.domain.social.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentedNotificationEventHandler {

    private final NotifierService notifierService;
    private final NotificationSaver notificationSaver;

    @Autowired
    public CommentedNotificationEventHandler(NotifierService notifierService, NotificationSaver notificationSaver) {
        this.notifierService = notifierService;
        this.notificationSaver = notificationSaver;
    }

    @Subscribe
    public void sendCommentedNotificationToPostAuthor(PostCommentedEvent postCommentedEvent) {
        PostCommentedEvent event = postCommentedEvent;
        Post post = event.getPost();
        String commentOwner = event.getCommentOwner();
        if (!post.isOwnedBy(commentOwner)) {
            Notification notification = notificationSaver.createCommentedNotificationFrom(event);
            notifierService.sendNotification(notification);
        }
    }

}
