package com.otchi.domain.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.Subscribe;
import com.otchi.application.ConnectedUserService;
import com.otchi.application.MailService;
import com.otchi.domain.services.PushNotificationsService;
import com.otchi.domain.social.models.Post;

@Service
public class PostNotificationEventHandler {

	private final PushNotificationsService pushNotificationsService;
	private final MailService mailService;
	private final ConnectedUserService connectedUserService;

	@Autowired
	public PostNotificationEventHandler(
			PushNotificationsService pushNotificationsService,
			MailService mailService, ConnectedUserService connectedUserService) {
		this.pushNotificationsService = Preconditions
				.checkNotNull(pushNotificationsService);
		this.mailService = Preconditions.checkNotNull(mailService);
		this.connectedUserService = Preconditions
				.checkNotNull(connectedUserService);
	}

	@Subscribe
	public void sendLikeNotificationToPostAuthor(
			PostNotificationEvent postNotifEvent) {
		PostNotificationEvent event = postNotifEvent;
		Post post = event.getPost();

		if (!connectedUserService.isUserConnected(event.getUser().getUsername())) {
			mailService.sendEmail(event.getUser());
		} else {
			pushNotificationsService.sendLikeNotificationToPostAuthor(post,
					event.getLikerUsername());
		}
	}
}
