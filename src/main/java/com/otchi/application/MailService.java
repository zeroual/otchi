package com.otchi.application;

import com.otchi.domain.social.models.Post;
import com.otchi.domain.users.models.User;

public interface MailService {

    void sendWelcomeEmail(User user);

    void sendLikedPostNotificationMail(User author, User liker, String summary, Long postId);

}
