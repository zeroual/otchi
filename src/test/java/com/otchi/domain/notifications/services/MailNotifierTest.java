package com.otchi.domain.notifications.services;

import com.otchi.application.MailService;
import com.otchi.application.UserService;
import com.otchi.domain.notifications.models.Notification;
import com.otchi.domain.users.models.User;
import org.junit.Test;

import static com.otchi.domain.notifications.models.NotificationType.LIKED;
import static java.util.Optional.of;
import static org.mockito.Mockito.*;

public class MailNotifierTest {

    private MailService mailService = mock(MailService.class);
    private UserService userService = mock(UserService.class);
    private MailNotifier mailNotifier = new MailNotifier(mailService,userService);

    @Test
    public void shouldSendMailWithNotificationInformation() {

        Notification notification = new Notification("reciever", "sender", 32L, LIKED);

        MailNotification mailNotification = new MailNotification();
        mailNotification.setPostId(32L);
        mailNotification.setType(LIKED);

        User sender = new User("sender");
        User reciever = new User("reciever");
        when(userService.findUserByUsername("sender")).thenReturn(of(sender));
        when(userService.findUserByUsername("reciever")).thenReturn(of(reciever));

        mailNotification.setFrom(sender);
        mailNotification.setTo(reciever);

        mailNotifier.sendMail(notification);

        verify(mailService).sendNotificationMail(mailNotification);
    }
}