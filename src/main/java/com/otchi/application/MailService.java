package com.otchi.application;

import com.otchi.domain.notifications.services.MailNotification;
import com.otchi.domain.users.models.User;

public interface MailService {

    void sendWelcomeEmail(User user);

    void sendNotificationMail(MailNotification mailNotification);

}
