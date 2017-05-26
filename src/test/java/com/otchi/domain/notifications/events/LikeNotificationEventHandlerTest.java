package com.otchi.domain.notifications.events;

import com.otchi.domain.notifications.models.Notification;
import com.otchi.domain.notifications.models.NotificationType;
import com.otchi.domain.notifications.services.NotificationSaver;
import com.otchi.domain.notifications.services.NotifierService;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.users.models.User;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

public class LikeNotificationEventHandlerTest {

    private NotifierService notifierService = mock(NotifierService.class);
    private NotificationSaver notificationSaver = mock(NotificationSaver.class);
    private LikeNotificationEventHandler likeNotificationEventHandler = new LikeNotificationEventHandler(notifierService, notificationSaver);

    @Test
    public void shouldCreateSaveLikeNotificationIfPostAuthorIsNotTheSameAsPostAuthor() {
        Notification notification = buildNotification();
        LikePostEvent postLikedEvent = buildLikeNotificationEvent("postOwner", "liker");
        when(notificationSaver.createLikeNotificationFrom(postLikedEvent)).thenReturn(notification);

        likeNotificationEventHandler.sendLikeNotificationToPostAuthor(postLikedEvent);
        verify(notificationSaver).createLikeNotificationFrom(postLikedEvent);
    }

    @Test
    public void shouldSendLikeNotificationIfPostAuthorIsNotTheSameAsPostAuthor() {

        Notification notification = buildNotification();

        LikePostEvent postLikedEvent = buildLikeNotificationEvent("postOwner", "liker");

        when(notificationSaver.createLikeNotificationFrom(postLikedEvent)).thenReturn(notification);

        likeNotificationEventHandler.sendLikeNotificationToPostAuthor(postLikedEvent);
        verify(notifierService).sendNotification(notification);
    }

    @Test
    public void shouldNotNotifyUserIsTheSameAsPostAuthor() {
        Notification notification = buildNotification();
        LikePostEvent postLikedEvent = buildLikeNotificationEvent("postOwner", "postOwner");

        when(notificationSaver.createLikeNotificationFrom(postLikedEvent)).thenReturn(notification);

        likeNotificationEventHandler.sendLikeNotificationToPostAuthor(postLikedEvent);
        verifyZeroInteractions(notifierService);
        verifyZeroInteractions(notificationSaver);

    }



    private Notification buildNotification() {
        return new Notification("toto", "fofo", 1L, NotificationType.LIKED);

    }

    private LikePostEvent buildLikeNotificationEvent(String postOwner, String likeOwner) {
        Post likedPost = new Post(LocalDateTime.now());
        likedPost.setAuthor(new User(postOwner));
        LikePostEvent postLikedEvent = new LikePostEvent(likedPost, likeOwner);
        return postLikedEvent;

    }

}