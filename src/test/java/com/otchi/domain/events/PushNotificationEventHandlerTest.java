package com.otchi.domain.events;

import com.otchi.application.MailService;
import com.otchi.application.UserService;
import com.otchi.domain.services.PushNotificationsService;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.users.models.User;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Optional.of;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class PushNotificationEventHandlerTest {

    private PushNotificationsService pushNotificationsService = mock(PushNotificationsService.class);
    private ConnectedUserService connectedUserService = mock(ConnectedUserService.class);
    private MailService mailService = mock(MailService.class);
    private UserService userService = mock(UserService.class);
    private PushNotificationEventHandler pushNotificationEventHandler = new PushNotificationEventHandler(pushNotificationsService, connectedUserService, mailService,
            userService);

    @Test
    public void shouldSendCommentedNotificationToPostAuthor() {
        Post commentedPost = new Post(LocalDateTime.now());
        String postOwner = "postOwner";
        commentedPost.setAuthor(new User(postOwner));
        String commentOwner = "commentOwner";
        PostCommentedEvent postCommentedEvent = new PostCommentedEvent(commentedPost, commentOwner);
        pushNotificationEventHandler.sendCommentedNotificationToPostAuthor(postCommentedEvent);
        verify(pushNotificationsService).sendCommentedNotificationToPostAuthor(eq(commentedPost), eq(commentOwner));
    }

    @Test
    public void shouldSendLikeNotificationToPostAuthor() {
        Post likedPost = new Post(LocalDateTime.now());
        String postOwner = "postOwner";
        likedPost.setAuthor(new User(postOwner));
        String likeOwner = "likeOwner";
        LikePostEvent postLikedEvent = new LikePostEvent(likedPost, likeOwner);
        pushNotificationEventHandler.sendLikeNotificationToPostAuthor(postLikedEvent);
        verify(pushNotificationsService).sendLikeNotificationToPostAuthor(eq(likedPost), eq(likeOwner));
    }

    @Test
    public void shouldSendEmailToAuthorIfIsNotConnected() {
        Post likedPost = new Post(LocalDateTime.now());
        String postOwner = "postOwner";
        likedPost.setAuthor(new User(postOwner));
        String likeOwner = "likeOwner";
        LikePostEvent postLikedEvent = new LikePostEvent(likedPost, likeOwner);

        User liker = new User(likeOwner);
        when(connectedUserService.isConnected(postOwner)).thenReturn(false);
        when(userService.findUserByUsername(likeOwner)).thenReturn(of(liker));
        pushNotificationEventHandler.sendLikeNotificationToPostAuthor(postLikedEvent);

        User author = likedPost.getAuthor();

        String summary = "";
        Long postId = likedPost.getId();

        verify(mailService, atLeastOnce()).sendLikedPostNotificationMail(author, liker,summary, postId);

    }
}
