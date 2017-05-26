package com.otchi.domain.notifications.services;

import com.otchi.application.ConnectedUsersService;
import com.otchi.domain.notifications.models.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotifierService {
    private final RealTimeNotifier realTimeNotifier;
    private final MailNotifier mailNotifier;
    private final ConnectedUsersService connectedUsersService;

    @Autowired
    public NotifierService(RealTimeNotifier realTimeNotifier,
                           MailNotifier mailNotifier,
                           ConnectedUsersService connectedUsersService) {
        this.realTimeNotifier = realTimeNotifier;
        this.mailNotifier = mailNotifier;
        this.connectedUsersService = connectedUsersService;
    }

    public void sendNotification(Notification notification) {

        String receiver = notification.getUsername();
        if (connectedUsersService.isConnected(receiver)) {
            realTimeNotifier.pushNotification(notification);
        } else {
            mailNotifier.sendMail(notification);
        }
    }

}
