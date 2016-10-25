package com.otchi.domain.events;

import com.otchi.application.ConnectedUserService;
import com.otchi.application.MailService;
import com.otchi.domain.services.PushNotificationsService;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.users.models.User;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PushNotificationEventHandlerTest {
	private MailService mailService ;
	private ConnectedUserService connectedUserService ;

    private PushNotificationsService pushNotificationsService ;
    private PushNotificationEventHandler pushNotificationEventHandler ;

    @Before
    public void init(){

    	pushNotificationsService = mock(PushNotificationsService.class);
    	mailService = mock(MailService.class);
    	connectedUserService = mock(ConnectedUserService.class);

        pushNotificationEventHandler =	new PushNotificationEventHandler(pushNotificationsService,
        		mailService, connectedUserService);
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void shouldSendCommentedNotificationToPostAuthor() {
        Post commentedPost = new Post(new Date());
        String commentOwner = "commentOwner";
        PostCommentedEvent postCommentedEvent = new PostCommentedEvent(commentedPost, commentOwner);
        pushNotificationEventHandler.sendCommentedNotificationToPostAuthor(postCommentedEvent);
        verify(pushNotificationsService).sendCommentedNotificationToPostAuthor(eq(commentedPost), eq(commentOwner));
    }

    @Test
    public void sendLikeNotificationToPostAuthorTest() {

        Post likedPost = new Post(new Date());
        String likerUsername = "likerUsername";
        User user = new User("email@fofo.com", "firstName_sample",
    			"lastName");
        PostNotificationEvent postNotificationEvent = new PostNotificationEvent(likedPost, user, likerUsername);

        // Expect
        Mockito.when(connectedUserService.isUserConnected("email@fofo.com")).thenReturn(true);

		// Action
        pushNotificationEventHandler.sendLikeNotificationToPostAuthor(postNotificationEvent);

		Mockito.verify(connectedUserService).isUserConnected("email@fofo.com");
		Mockito.verify(pushNotificationsService).sendLikeNotificationToPostAuthor(eq(likedPost), eq(likerUsername));
    }
    @Test
    public void sendMailNotificationToPostAuthorTest() {
        Post likedPost = new Post(new Date());
        String likerUsername = "likerUsername";
        User user = new User("email@fofo.com", "firstName_sample",
    			"email@fofo.com");
        PostNotificationEvent postNotificationEvent = new PostNotificationEvent(likedPost,
        		user, likerUsername);

        // Expect
		Mockito.when(connectedUserService.isUserConnected("email@fofo.com")).thenReturn(false);

		// Action
        pushNotificationEventHandler.sendLikeNotificationToPostAuthor(postNotificationEvent);

		Mockito.verify(connectedUserService).isUserConnected("email@fofo.com");
        Mockito.verify(mailService).sendEmail(user);
    }
}