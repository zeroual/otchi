package com.otchi.domain.notifications.services;

import com.otchi.application.ConnectedUsersService;
import com.otchi.domain.notifications.models.Notification;
import org.junit.Before;
import org.junit.Test;

import static com.otchi.domain.notifications.models.NotificationType.LIKED;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class NotifierServiceTest {

    private NotifierService notifierService;

    private ConnectedUsersService connectedUsersService = mock(ConnectedUsersService.class);
    private RealTimeNotifier realTimeNotifier = mock(RealTimeNotifier.class);
    private MailNotifier mailNotifier = mock(MailNotifier.class);
    private String receiver;
    private Notification notification;

    @Before
    public void initialize() {

        this.notifierService = new NotifierService(realTimeNotifier, mailNotifier, connectedUsersService);
        this.receiver = "receiver";
        this.notification = new Notification(receiver, "", 1L, LIKED);
    }

    @Test
    public void shouldSendRealTimeNotificationToUserIfIsConnected() {

        when(connectedUsersService.isConnected(receiver)).thenReturn(true);
        notifierService.sendNotification(notification);
        verify(realTimeNotifier).pushNotification(any());
    }

    @Test
    public void shouldSendAnEmailNotificationToUserIfIsNotConnected() {

        when(connectedUsersService.isConnected(receiver)).thenReturn(false);
        notifierService.sendNotification(notification);
        verify(mailNotifier).sendMail(any());
    }

    @Test
    public void shouldSendOnlyRealTimeNotificationIfUserIsConnected() {

        when(connectedUsersService.isConnected(receiver)).thenReturn(true);
        notifierService.sendNotification(notification);
        verify(realTimeNotifier).pushNotification(any());
        verifyZeroInteractions(mailNotifier);
    }

    @Test
    public void shouldSendOnlyAMailNotificationIfUserIsNotConnected() {

        when(connectedUsersService.isConnected(receiver)).thenReturn(false);
        notifierService.sendNotification(notification);
        verify(mailNotifier).sendMail(any());
        verifyZeroInteractions(realTimeNotifier);
    }

}