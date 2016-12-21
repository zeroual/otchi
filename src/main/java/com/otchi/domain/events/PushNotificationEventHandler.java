package com.otchi.domain.events;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.Subscribe;
import com.otchi.application.ConnectedUserService;
import com.otchi.application.MailService;
import com.otchi.domain.services.PushNotificationsService;
import com.otchi.domain.social.models.Post;
import com.otchi.domain.users.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PushNotificationEventHandler {

	private final PushNotificationsService pushNotificationsService;
	private final MailService mailService;
	private final ConnectedUserService connectedUserService;

	@Autowired
	public PushNotificationEventHandler(
			PushNotificationsService pushNotificationsService,
			MailService mailService,
			ConnectedUserService connectedUserService) {
		this.pushNotificationsService = pushNotificationsService;
		this.mailService =mailService;
		this.connectedUserService=connectedUserService;

	}

    @Subscribe
    public void sendCommentedNotificationToPostAuthor(PostCommentedEvent postCommentedEvent) {
        PostCommentedEvent event = postCommentedEvent;
        Post post = event.getPost();
        String commentOwner = event.getCommentOwner();
        pushNotificationsService.sendCommentedNotificationToPostAuthor(post, commentOwner);
    }

    @Subscribe
    public void sendLikeNotificationToPostAuthor(LikePostEvent likePostEvent) {
    	LikePostEvent event = likePostEvent;
		Post post = event.getLikedPost();
		User user = post.getAuthor();
		String username = user.getUsername();
		String likerUsername = event.getLikeOwner();

		if (!connectedUserService.isUserConnected(username)) {
			mailService.sendLikePostNotificationEmail( post, likerUsername);
		} else {
			pushNotificationsService.sendLikeNotificationToPostAuthor(post,
					likerUsername);
		}
	}
}
