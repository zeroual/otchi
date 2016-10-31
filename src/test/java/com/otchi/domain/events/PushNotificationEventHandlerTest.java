package com.otchi.domain.events;

import com.otchi.domain.services.PushNotificationsService;
import com.otchi.domain.social.models.Post;
import org.junit.Test;

import java.util.Date;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PushNotificationEventHandlerTest {

    private PushNotificationsService pushNotificationsService = mock(PushNotificationsService.class);
    private PushNotificationEventHandler pushNotificationEventHandler = new PushNotificationEventHandler(pushNotificationsService);

    @Test
    public void shouldSendCommentedNotificationToPostAuthor() {
        Post commentedPost = new Post(new Date());
        String commentOwner = "commentOwner";
        PostCommentedEvent postCommentedEvent = new PostCommentedEvent(commentedPost, commentOwner);
        pushNotificationEventHandler.sendCommentedNotificationToPostAuthor(postCommentedEvent);
        verify(pushNotificationsService).sendCommentedNotificationToPostAuthor(eq(commentedPost), eq(commentOwner));
    }

    @Test
    public void shouldSendLikeNotificationToPostAuthor(){
        Post likedPost = new Post(new Date());
        String likeOwner = "likeOwner";
        LikePostEvent postLikedEvent = new LikePostEvent(likedPost, likeOwner);
        pushNotificationEventHandler.sendLikeNotificationToPostAuthor(postLikedEvent);
        verify(pushNotificationsService).sendLikeNotificationToPostAuthor(eq(likedPost), eq(likeOwner));
    }
}