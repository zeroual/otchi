package com.otchi.domain.events;

import com.google.common.eventbus.Subscribe;
import com.otchi.domain.services.PushNotificationsService;
import com.otchi.domain.social.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PushNotificationEventHandler {

    private final PushNotificationsService pushNotificationsService;

    @Autowired
    public PushNotificationEventHandler(PushNotificationsService pushNotificationsService) {
        this.pushNotificationsService = pushNotificationsService;
    }

    @Subscribe
    public void sendCommentedNotificationToPostAuthor(PostCommentedEvent postCommentedEvent) {
        PostCommentedEvent event = postCommentedEvent;
        Post post = event.getPost();
        String commentOwner = event.getCommentOwner();
        pushNotificationsService.sendCommentedNotificationToPostAuthor(post, commentOwner);
    }

    @Subscribe
    public void sendLikeNotificationToPostAuthor(LikePostEvent likePostEvent){
        LikePostEvent event = likePostEvent;
        Post likedPost = event.getLikedPost();
        String likeOwner = event.getLikeOwner();
        pushNotificationsService.sendLikeNotificationToPostAuthor(likedPost, likeOwner);
    }

}
