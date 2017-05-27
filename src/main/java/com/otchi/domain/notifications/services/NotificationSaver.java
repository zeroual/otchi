package com.otchi.domain.notifications.services;

import com.otchi.application.utils.Clock;
import com.otchi.domain.notifications.events.LikePostEvent;
import com.otchi.domain.notifications.events.PostCommentedEvent;
import com.otchi.domain.notifications.models.Notification;
import com.otchi.domain.notifications.models.NotificationsRepository;
import com.otchi.domain.social.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.otchi.domain.notifications.models.NotificationType.COMMENT_ON_POST;
import static com.otchi.domain.notifications.models.NotificationType.LIKED;

@Service
public class NotificationSaver {

    private final NotificationsRepository notificationsRepository;
    private final Clock clock;

    @Autowired
    public NotificationSaver(NotificationsRepository notificationsRepository, Clock clock) {

        this.notificationsRepository = notificationsRepository;
        this.clock = clock;
    }


    public Notification createLikeNotificationFrom(LikePostEvent postLikedEvent) {
        String likeOwner = postLikedEvent.getLikeOwner();

        Post likedPost = postLikedEvent.getLikedPost();
        String postAuthor = likedPost.getAuthor().getUsername();
        Long postId = likedPost.getId();

        Notification notification = new Notification(postAuthor, likeOwner, postId, LIKED);
        notification.changeCreationDateTo(clock.now());
        return notificationsRepository.save(notification);
    }

    public Notification createCommentedNotificationFrom(PostCommentedEvent commentEvent) {
        String commentOwner = commentEvent.getCommentOwner();

        Post post = commentEvent.getPost();
        String postAuthor = post.getAuthor().getUsername();
        Long postId = post.getId();

        Notification notification = new Notification(postAuthor, commentOwner, postId, COMMENT_ON_POST);
        notification.changeCreationDateTo(clock.now());
        return notificationsRepository.save(notification);
    }
}
