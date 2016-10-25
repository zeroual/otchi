package com.otchi.domain.events;

import com.otchi.application.ConnectedUserService;
import com.otchi.application.MailService;
import com.otchi.domain.services.PushNotificationsService;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.users.models.User;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.Optional;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PostNotificationEventHandlerTest {

	private PushNotificationsService pushNotificationsService ;
	private MailService mailService ;
	private ConnectedUserService connectedUserService ;
	
    private PostNotificationEventHandler postNotifEventHandler ;

    @Before
    public void init(){

    	pushNotificationsService = mock(PushNotificationsService.class);
    	mailService = mock(MailService.class);
    	connectedUserService = mock(ConnectedUserService.class);
    	
        postNotifEventHandler =	new PostNotificationEventHandler(pushNotificationsService, 
        		mailService, connectedUserService);
        MockitoAnnotations.initMocks(this);
    	
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
		postNotifEventHandler.sendLikeNotificationToPostAuthor(postNotificationEvent);

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
		postNotifEventHandler.sendLikeNotificationToPostAuthor(postNotificationEvent);

		Mockito.verify(connectedUserService).isUserConnected("email@fofo.com");
        Mockito.verify(mailService).sendEmail(user);
    }
}