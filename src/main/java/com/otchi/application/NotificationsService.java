package com.otchi.application;

import com.otchi.domain.social.models.Post;

public interface NotificationsService {

    void sendLikeNotificationToPostAuthor(Post post, String likerUsername);

}
