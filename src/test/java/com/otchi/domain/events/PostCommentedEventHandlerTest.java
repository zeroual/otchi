package com.otchi.domain.events;

import com.otchi.domain.services.PushNotificationsService;
import com.otchi.domain.social.models.Post;
import org.junit.Test;

import java.util.Date;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PostCommentedEventHandlerTest {

    private PushNotificationsService pushNotificationsService = mock(PushNotificationsService.class);
    private PostCommentedEventHandler postCommentedEventHandler = new PostCommentedEventHandler(pushNotificationsService);

    @Test
    public void shouldSendCommentedNotificationToPostAuthor() {
        Post commentedPost = new Post(new Date());
        String commentOwner = "commentOwner";
        PostCommentedEvent postCommentedEvent = new PostCommentedEvent(commentedPost, commentOwner);
        postCommentedEventHandler.sendCommentedNotificationToPostAuthor(postCommentedEvent);
        verify(pushNotificationsService).sendCommentedNotificationToPostAuthor(eq(commentedPost), eq(commentOwner));
    }
}