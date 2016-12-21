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

	private final MailService mailService = mock(MailService.class);
	private final ConnectedUserService connectedUserService = mock(ConnectedUserService.class);
    private final PushNotificationsService pushNotificationsService = mock(PushNotificationsService.class);
    private PushNotificationEventHandler pushNotificationEventHandler ;
   
    @Before
    public void init(){
    	pushNotificationEventHandler = new PushNotificationEventHandler
    			(pushNotificationsService, mailService, connectedUserService);

    	MockitoAnnotations.initMocks(this);
    	
    }
    
    @Test
    public void shouldSendCommentedNotificationToPostAuthor() {
        Post commentedPost = new Post(new Date());
        String commentOwner = "commentOwner";
        PostCommentedEvent postCommentedEvent = new PostCommentedEvent(commentedPost, commentOwner);
        pushNotificationEventHandler.sendCommentedNotificationToPostAuthor(postCommentedEvent);
        Mockito.verify(pushNotificationsService).sendCommentedNotificationToPostAuthor(eq(commentedPost), eq(commentOwner));
    }
    
    @Test
    public void sendLikeNotificationToPostAuthorTest() {

        Post likedPost = new Post(new Date());
        String likerUsername = "likerUsername";
        User user = new User("email@fofo.com", "firstName_sample",
    			"lastName");

        LikePostEvent likPostEvent = new LikePostEvent(likedPost, likerUsername);
        
        // Expect
        Mockito.when(connectedUserService.isUserConnected("email@fofo.com")).thenReturn(true);

		// Action
        pushNotificationEventHandler.sendLikeNotificationToPostAuthor(likPostEvent);

		Mockito.verify(connectedUserService).isUserConnected("email@fofo.com");
		Mockito.verify(pushNotificationsService).sendLikeNotificationToPostAuthor(eq(likedPost), eq(likerUsername));
    }
    @Test
    public void sendMailNotificationToPostAuthorTest() {
        Post likedPost = new Post(new Date());
        String likerUsername = "likerUsername";
        User user = new User("email@fofo.com", "firstName_sample",
    			"email@fofo.com");
        LikePostEvent likPostEvent = new LikePostEvent(likedPost, likerUsername);
        
        // Expect 
		Mockito.when(connectedUserService.isUserConnected("email@fofo.com")).thenReturn(false);

		// Action
		pushNotificationEventHandler.sendLikeNotificationToPostAuthor(likPostEvent);

		Mockito.verify(connectedUserService).isUserConnected("email@fofo.com");
        Mockito.verify(mailService).sendLikePostNotificationEmail(likedPost, "likerUsername");
    }
}