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

public class CommentedNotificationEventHandlerTest {


    private NotifierService notifierService = mock(NotifierService.class);
    private NotificationSaver notificationSaver = mock(NotificationSaver.class);
    private CommentedNotificationEventHandler commentedNotificationEventHandler = new CommentedNotificationEventHandler(notifierService, notificationSaver);

    @Test
    public void shouldCreateAndSaveCommentedNotificationIfAuthorIsNotTheCommenter() {
        Notification notification = buildNotification();
        PostCommentedEvent postCommentedEvent = buildCommentedNotificationEvent("postOwner", "commenter");
        when(notificationSaver.createCommentedNotificationFrom(postCommentedEvent)).thenReturn(notification);

        commentedNotificationEventHandler.sendCommentedNotificationToPostAuthor(postCommentedEvent);
        verify(notificationSaver).createCommentedNotificationFrom(postCommentedEvent);
    }

    @Test
    public void shouldSendCommentedNotificationIfAuthorIsNotTheCommenter() {
        Notification notification = buildNotification();
        PostCommentedEvent postCommentedEvent = buildCommentedNotificationEvent("postOwner", "commenter");
        when(notificationSaver.createCommentedNotificationFrom(postCommentedEvent)).thenReturn(notification);

        commentedNotificationEventHandler.sendCommentedNotificationToPostAuthor(postCommentedEvent);
        verify(notifierService).sendNotification(notification);
    }

    @Test
    public void shouldNotNotifyUserIsTheSameAsTheCommenter() {
        Notification notification = buildNotification();
        PostCommentedEvent postCommentedEvent = buildCommentedNotificationEvent("postOwner", "postOwner");

        when(notificationSaver.createCommentedNotificationFrom(postCommentedEvent)).thenReturn(notification);

        commentedNotificationEventHandler.sendCommentedNotificationToPostAuthor(postCommentedEvent);
        verifyZeroInteractions(notifierService);
        verifyZeroInteractions(notificationSaver);

    }


    private Notification buildNotification() {
        return new Notification("toto", "fofo", 1L, NotificationType.LIKED);

    }

    private PostCommentedEvent buildCommentedNotificationEvent(String postOwner, String commenter) {
        Post post = new Post(LocalDateTime.now());
        post.setAuthor(new User(postOwner));
        return new PostCommentedEvent(post, commenter);
    }

}