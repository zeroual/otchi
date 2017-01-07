package com.otchi.infrastructure.mail;

import com.otchi.application.MailService;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.users.models.User;

public class MockMailService implements MailService {

    @Override
    public void sendWelcomeEmail(User user) {

    }

    @Override
    public void sendLikedPostNotificationMail(User author, User liker, String summary, Long postId) {

    }

}
