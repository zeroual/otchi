package com.otchi.domain.events;

import com.otchi.application.MailService;
import com.otchi.application.UserService;
import com.otchi.domain.mail.MailParameter;
import com.otchi.domain.services.PushNotificationsService;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.social.models.Story;
import com.otchi.domain.users.models.User;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Optional.of;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
        when(connectedUserService.isConnected(postOwner)).thenReturn(true);
        LikePostEvent postLikedEvent = new LikePostEvent(likedPost, likeOwner);
        pushNotificationEventHandler.sendLikeNotificationToPostAuthor(postLikedEvent);
        verify(pushNotificationsService).sendLikeNotificationToPostAuthor(eq(likedPost), eq(likeOwner));
    }

    @Test
    public void shouldSendEmailToAuthorIfIsNotConnected() {
        Post likedPost = mock(Post.class);
        String postOwner = "postOwner";
        String likeOwner = "likeOwner";
        String summary = "blabla";
        LikePostEvent postLikedEvent = new LikePostEvent(likedPost, likeOwner);
        User liker = new User(likeOwner);
        User author = new User(postOwner);
        Long postId = new Long(928839L);
        
        // expect
        when(likedPost.getAuthor()).thenReturn(author);
        when(likedPost.getPostContent()).thenReturn(new Story(summary));
        when(likedPost.getId()).thenReturn(postId);
        when(connectedUserService.isConnected(postOwner)).thenReturn(false);
        when(userService.findUserByUsername(likeOwner)).thenReturn(of(liker));
        
        // action
        pushNotificationEventHandler.sendLikeNotificationToPostAuthor(postLikedEvent);

        verify(mailService, atLeastOnce()).sendLikedPostNotificationMail(
        		new MailParameter(author, liker, summary, postId));

    }
}
