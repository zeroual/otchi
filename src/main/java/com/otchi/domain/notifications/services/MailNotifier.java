package com.otchi.domain.notifications.services;


import com.otchi.application.MailService;
import com.otchi.application.UserService;
import com.otchi.domain.notifications.models.Notification;
import com.otchi.domain.users.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MailNotifier {

    private final MailService mailService;
    private final UserService userService;

    @Autowired
    public MailNotifier(MailService mailService, UserService userService) {

        this.mailService = mailService;
        this.userService = userService;
    }

    public void sendMail(Notification notification) {

        MailNotification mailNotification = new MailNotification();
        mailNotification.setPostId(notification.postId());
        mailNotification.setType(notification.getType());

        Optional<User> reciever = userService.findUserByUsername(notification.getUsername());
        Optional<User> sender = userService.findUserByUsername(notification.senderId());

        mailNotification.setFrom(sender.get());
        mailNotification.setTo(reciever.get());
        mailService.sendNotificationMail(mailNotification);
    }
}
