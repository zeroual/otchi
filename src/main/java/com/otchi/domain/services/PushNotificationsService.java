package com.otchi.domain.services;

import com.otchi.domain.social.models.Notification;
import com.otchi.domain.social.models.Post;

public interface PushNotificationsService {

    Notification sendLikeNotificationToPostAuthor(Post post, String likerUsername);

    Notification sendCommentedNotificationToPostAuthor(Post post, String commentOwner);

}
